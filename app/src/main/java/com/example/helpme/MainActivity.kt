package com.example.helpme

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.example.helpme.navigation.AppNavigation
import com.example.helpme.ui.theme.HelpMeTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HelpMeTheme {
                val navController = rememberNavController()
                AppNavigation(navController = navController)
            }
        }
    }
}
