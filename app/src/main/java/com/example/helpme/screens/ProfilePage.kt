package com.example.helpme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.helpme.StorageManager

@Composable
fun ProfilePage(navController: NavController) {
    var key by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    StorageManager.getUserr("+998") {
        key = it
    }
    Column {
        Button(onClick = {
            StorageManager.saveUser(context, "")
            navController.navigate("SignIn")
        }) {
            Text("Log Out")
        }
        Text("Your id: $key")
    }
}