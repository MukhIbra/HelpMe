package com.example.helpme.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.glance.text.Text
import androidx.navigation.NavController

@Composable
fun SOSScreen(navController: NavController, key :List<String>){

    LazyColumn {
        items(key){
            Text(text = it)
        }

    }
}