package com.kindkrank.app.data

import com.kindkrank.app.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

object GitHubIssueService {

    suspend fun createIssue(title: String, body: String): Result<Unit> = withContext(Dispatchers.IO) {
        runCatching {
            val apiUrl = "https://api.github.com/repos/${BuildConfig.GITHUB_REPO_OWNER}/${BuildConfig.GITHUB_REPO_NAME}/issues"
            val connection = URL(apiUrl).openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer ${BuildConfig.GITHUB_TOKEN}")
            connection.setRequestProperty("Accept", "application/vnd.github+json")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28")
            connection.doOutput = true

            val json = JSONObject().apply {
                put("title", title)
                put("body", body)
                put("labels", org.json.JSONArray().put("user-feedback"))
            }.toString()

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(json)
                writer.flush()
            }

            val responseCode = connection.responseCode
            if (responseCode != HttpURLConnection.HTTP_CREATED) {
                val errorBody = runCatching {
                    connection.errorStream?.bufferedReader()?.readText()
                }.getOrNull().orEmpty()
                error("GitHub API returned HTTP $responseCode: $errorBody")
            }
        }
    }
}
