package com.example.helpme.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.helpme.R
import com.example.helpme.StorageManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeParentScreen(navController: NavController) {
    var users by remember { mutableStateOf<List<String>>(emptyList()) }

    StorageManager.getChilds(StorageManager.getSavedUser(LocalContext.current)) { list ->
        users = list
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(containerColor = colorResource(R.color.yellow), onClick = {
            navController.navigate("Add")
        }) {
            Icon(Icons.Filled.Add, "Add")
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(17.dp)
                .padding(innerPadding)
        ) {
            items(users) { item ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(1.dp, RoundedCornerShape(4.dp))
                        .padding(horizontal = 10.dp, vertical = 10.dp),
                ) {
                    Text(item, fontSize = 30.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(navController: NavController) {
    var key by remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Add Child", fontSize = 32.sp, modifier = Modifier.padding(bottom = 15.dp)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 13.dp),
            value = key,
            onValueChange = {
                key = it
            },
            label = { Text(text = "Your Key") },
            placeholder = { Text(text = "Key") },
        )
        Button(modifier = Modifier.padding(top = 27.dp), onClick = {
            StorageManager.checkChild(key.text) { result ->
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                if (result == "Success") StorageManager.addChild(context, key.text)
            }
            navController.navigate("Home")
        }) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 5.dp),
                text = "Submit",
                fontSize = 19.sp
            )
        }
    }
}