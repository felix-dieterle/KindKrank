package com.kindkrank.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kindkrank.app.navigation.AppNavigation
import com.kindkrank.app.ui.theme.KindKrankTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KindKrankTheme {
                AppNavigation()
            }
        }
    }
}
