package com.example.helpme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun SignInScreen(navController: NavController) {
    val context = LocalContext.current
    var username by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.black)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = "Sign In",
            color = colorResource(R.color.white),
            fontSize = 45.sp
        )
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 5.dp),
            value = username,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(R.color.white),
                focusedBorderColor = colorResource(R.color.yellow),
                unfocusedBorderColor = colorResource(R.color.yellow),
                focusedLabelColor = colorResource(R.color.white),
                unfocusedLabelColor = colorResource(R.color.white),
            ),
            shape = RoundedCornerShape(10.dp),
            label = { Text("Your Phone Number", color = colorResource(R.color.white)) },
            placeholder = { Text("Phone Number", color = colorResource(R.color.white)) },
            onValueChange = {
                username = it
            })
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 5.dp),
            value = password,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = colorResource(R.color.white),
                focusedBorderColor = colorResource(R.color.yellow),
                unfocusedBorderColor = colorResource(R.color.yellow),
                focusedLabelColor = colorResource(R.color.white),
                unfocusedLabelColor = colorResource(R.color.white),
            ),
            shape = RoundedCornerShape(10.dp),
            label = { Text("Your Password", color = colorResource(R.color.white)) },
            placeholder = { Text("Password", color = colorResource(R.color.white)) },
            onValueChange = {
                password = it
            })
        Button(modifier = Modifier.padding(vertical = 8.dp),
            colors = ButtonDefaults.buttonColors(colorResource(R.color.yellow)),
            onClick = {
                StorageManager.getUser(username, password) { result ->
                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
                    if (result == "Successful Login") {
                        StorageManager.saveUser(context, username.text)
                        navController.navigate("Home")
                    }
                }
            }) {
            Text(
                modifier = Modifier.padding(vertical = 7.dp, horizontal = 15.dp),
                text = "Sign In",
                fontSize = 20.sp,
                color = colorResource(R.color.black)
            )
        }
        Button(colors = ButtonDefaults.buttonColors(colorResource(R.color.black)), onClick = {
            navController.navigate("SignUp")
        }) {
            Text("Do not have an account", color = colorResource(R.color.white))
        }
    }
}