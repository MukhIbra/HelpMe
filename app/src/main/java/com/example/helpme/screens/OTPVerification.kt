package com.example.helpme.screens

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.helpme.R
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun OTPVerificationScreen(navController: NavController, number: String) {

    val passwordVisibility = remember { mutableStateOf(false) }
    val otp = rememberSaveable {
        mutableStateOf("")
    }
    val context = LocalContext.current
    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    AnimatedVisibility(visible = true){
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(horizontal = 36.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(42.dp))
            TextField(
//                enabled = !loading.value,
                value = otp.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length <= 6) otp.value = it },
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = "Enter your otp") },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                supportingText = {
                    Text(
                        text = "${otp.value.length} / 6",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.End,
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    containerColor = Color.Black
                ),
                textStyle = TextStyle(fontSize = 16.sp),
                visualTransformation =
                if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility.value) R.drawable.password_toggle_hide
                    else R.drawable.password_toggle

                    val description =
                        if (passwordVisibility.value) "Hide password" else "Show password"

                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(painter = painterResource(id = image), description, tint = Color.Black)
                    }
                }
            )


            Spacer (modifier = Modifier.height(10.dp))

            Button (
//                enabled = !loading.value,
                onClick = {
                    if (TextUtils.isEmpty(otp.value) || otp.value.length < 6) {
                        Toast.makeText(
                            context,
                            "Please enter a valid OTP",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
//                        loading.value = true
                        //This is the main part where we verify the OTP
                        val credential: PhoneAuthCredential =
                            PhoneAuthProvider.getCredential(
                                number, otp.value
                            )//Get credential object
                        mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(context as Activity) { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Your mobile phone is verified successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    navController.navigate("Home")
                                } else {
//                                    loading.value = false
                                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                                        Toast.makeText(
                                            context,
                                            "Verification failed.." + (task.exception as FirebaseAuthInvalidCredentialsException).message,
                                            Toast.LENGTH_LONG
                                        ).show()
                                        if ((task.exception as FirebaseAuthInvalidCredentialsException).message?.contains(
                                                "expired"
                                            ) == true
                                        ) {//If code is expired then get a chance to resend the code
//                                            codeSent.value = false
                                            otp.value = ""
                                        }
                                    }
                                }
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Verify OTP", modifier = Modifier.padding(8.dp))
            }

        }
    }


}


