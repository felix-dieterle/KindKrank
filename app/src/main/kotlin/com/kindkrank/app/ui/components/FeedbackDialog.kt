package com.kindkrank.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kindkrank.app.data.GitHubIssueService
import com.kindkrank.app.ui.theme.ErrorRed
import com.kindkrank.app.ui.theme.PrimaryBlue
import com.kindkrank.app.ui.theme.SuccessGreen
import kotlinx.coroutines.launch

@Composable
fun FeedbackDialog(onDismiss: () -> Unit) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    AlertDialog(
        onDismissRequest = { if (!isLoading) onDismiss() },
        title = {
            Text(
                text = "💡 Verbesserung melden",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
        },
        text = {
            Column {
                if (successMessage != null) {
                    Text(
                        text = successMessage!!,
                        color = SuccessGreen,
                        fontSize = 15.sp,
                    )
                } else {
                    Text(
                        text = "Haben Sie eine Idee oder einen Fehler entdeckt? Teilen Sie es uns mit!",
                        fontSize = 14.sp,
                    )
                    Spacer(Modifier.height(12.dp))
                    OutlinedTextField(
                        value = title,
                        onValueChange = { title = it },
                        label = { Text("Kurzbeschreibung") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(8.dp),
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text("Details (optional)") },
                        minLines = 3,
                        maxLines = 5,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(8.dp),
                    )
                    if (errorMessage != null) {
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = errorMessage!!,
                            color = ErrorRed,
                            fontSize = 13.sp,
                        )
                    }
                    if (isLoading) {
                        Spacer(Modifier.height(12.dp))
                        CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(4.dp),
                            color = PrimaryBlue,
                        )
                    }
                }
            }
        },
        confirmButton = {
            if (successMessage != null) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                ) {
                    Text("Schließen")
                }
            } else {
                Button(
                    onClick = {
                        if (title.isBlank()) {
                            errorMessage = "Bitte eine Kurzbeschreibung eingeben."
                            return@Button
                        }
                        errorMessage = null
                        isLoading = true
                        scope.launch {
                            val result = GitHubIssueService.createIssue(
                                title = title.trim(),
                                body = description.trim(),
                            )
                            isLoading = false
                            if (result.isSuccess) {
                                successMessage = "✅ Vielen Dank! Ihr Feedback wurde erfolgreich übermittelt."
                            } else {
                                errorMessage = "Übermittlung fehlgeschlagen. Bitte später erneut versuchen."
                            }
                        }
                    },
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue),
                ) {
                    Text("Absenden")
                }
            }
        },
        dismissButton = {
            if (successMessage == null) {
                TextButton(
                    onClick = onDismiss,
                    enabled = !isLoading,
                ) {
                    Text("Abbrechen")
                }
            }
        },
        shape = RoundedCornerShape(16.dp),
    )
}
