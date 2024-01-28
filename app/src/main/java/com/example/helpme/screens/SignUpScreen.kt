package com.example.helpme.screens

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.helpme.R
import com.example.helpme.StorageManager
import com.example.helpme.User
import com.example.helpme.data.LocationDetails
import com.example.joshqinshop.networking.APIClient
import com.example.joshqinshop.networking.APIService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit


var locationCallback: LocationCallback? = null
var fusedLocationClient: FusedLocationProviderClient? = null
var locationRequired = false

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(navController: NavController) {


    val context = LocalContext.current
    var fullname by remember { mutableStateOf(TextFieldValue("")) }
    var age by remember { mutableStateOf(TextFieldValue("")) }
    var place by remember { mutableStateOf(TextFieldValue("")) }
    var phoneNumber by remember { mutableStateOf(TextFieldValue("")) }
    var password by remember { mutableStateOf(TextFieldValue("")) }
    val selectedOption = remember { mutableStateOf("Children") }

    var currUserNumber = StorageManager.getSavedUser(context = context)
    var currentLocation by remember {
        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble(), currUserNumber))
    }

    val otp = rememberSaveable {
        mutableStateOf("")
    }

    val verificationID = rememberSaveable {
        mutableStateOf("")
    }//Need this to get credentials

    val codeSent = rememberSaveable {
        mutableStateOf(false)
    }//Optional- Added just to make consistent UI

    val loading = rememberSaveable {
        mutableStateOf(false)
    }//Optional


    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var visible by rememberSaveable { mutableStateOf(true) }
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize(),
        visible = !codeSent.value && !loading.value,
        exit = scaleOut(
            targetScale = 0.5f,
            animationSpec = tween(durationMillis = 500, delayMillis = 100)
        ),
        enter = scaleIn(
            initialScale = 0.5f,
            animationSpec = tween(durationMillis = 500, delayMillis = 100)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.white)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {


            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = "Sign Up",
                color = colorResource(R.color.dark_blue),
                fontSize = 45.sp,
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 5.dp),
                value = fullname,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(R.color.black),
                    focusedBorderColor = colorResource(R.color.dark_blue),
                    unfocusedBorderColor = colorResource(R.color.dark_blue),
                    focusedLabelColor = colorResource(R.color.dark_blue),
                    unfocusedLabelColor = colorResource(R.color.dark_blue),
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text("Your Full Name", color = colorResource(R.color.black)) },
                placeholder = { Text("Full Name", color = colorResource(R.color.black)) },
                onValueChange = {
                    fullname = it
                })
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 5.dp),
                value = age,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(R.color.black),
                    focusedBorderColor = colorResource(R.color.dark_blue),
                    unfocusedBorderColor = colorResource(R.color.dark_blue),
                    focusedLabelColor = colorResource(R.color.dark_blue),
                    unfocusedLabelColor = colorResource(R.color.dark_blue),
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text("Your Age", color = colorResource(R.color.black)) },
                placeholder = { Text("Age", color = colorResource(R.color.black)) },
                onValueChange = {
                    age = it
                })
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 5.dp),
                value = place,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(R.color.black),
                    focusedBorderColor = colorResource(R.color.dark_blue),
                    unfocusedBorderColor = colorResource(R.color.dark_blue),
                    focusedLabelColor = colorResource(R.color.dark_blue),
                    unfocusedLabelColor = colorResource(R.color.dark_blue),
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text("Your School or Company", color = colorResource(R.color.black)) },
                placeholder = { Text("School or Company", color = colorResource(R.color.black)) },
                onValueChange = {
                    place = it
                })
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 5.dp),
                value = phoneNumber,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(R.color.black),
                    focusedBorderColor = colorResource(R.color.dark_blue),
                    unfocusedBorderColor = colorResource(R.color.dark_blue),
                    focusedLabelColor = colorResource(R.color.dark_blue),
                    unfocusedLabelColor = colorResource(R.color.dark_blue),
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text("Your Phone Number", color = colorResource(R.color.black)) },
                placeholder = { Text("Phone Number", color = colorResource(R.color.black)) },
                onValueChange = {
                    phoneNumber = it
                })
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(
                    Color.White,
                    shape = RoundedCornerShape(10.dp)
                ),
                value = password,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = colorResource(R.color.black),
                    focusedBorderColor = colorResource(R.color.dark_blue),
                    unfocusedBorderColor = colorResource(R.color.dark_blue),
                    focusedLabelColor = colorResource(R.color.dark_blue),
                    unfocusedLabelColor = colorResource(R.color.dark_blue),
                ),
                shape = RoundedCornerShape(10.dp),
                label = { Text("Your Password", color = colorResource(R.color.black)) },
                placeholder = { Text("Password", color = colorResource(R.color.black)) },
                onValueChange = {
                    password = it
                })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption.value == "Parent",
                    onClick = { selectedOption.value = "Parent" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.dark_blue),
                        unselectedColor = colorResource(R.color.grey),
                    )
                )
                Text("Parent", color = Color.Black)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                RadioButton(
                    selected = selectedOption.value == "Children",
                    onClick = { selectedOption.value = "Children" },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = colorResource(R.color.dark_blue),
                        unselectedColor = colorResource(R.color.grey),
                    )
                )
                Text("Children", color = Color.Black)
            }
            Button(modifier = Modifier.padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(colorResource(R.color.light_blue)),
                onClick = {
                    if (phoneNumber.text.length != 13 || " " in phoneNumber.text || phoneNumber.text.subSequence(
                            0, 4
                        ) != "+998"
                    ) {
                        Toast.makeText(context, "Invalid Phone Number", Toast.LENGTH_SHORT).show()
                    } else {
                        StorageManager.checkUser(phoneNumber.text) {
                            if (it) {
                                StorageManager.createUser(
                                    User(
                                        fullname.text,
                                        phoneNumber.text,
                                        password.text,
                                        selectedOption.value
                                    )
                                )
                                StorageManager.saveUser(context, phoneNumber.text)
                                Toast.makeText(context, "Successful Sign Up", Toast.LENGTH_SHORT)
                                    .show()

                            } else {
                                Toast.makeText(
                                    context,
                                    "Username already exists",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                        visible = !visible
                        if (TextUtils.isEmpty(phoneNumber.text) || phoneNumber.text.length < 10) {
                            Toast.makeText(
                                context,
                                "Enter a valid phone number",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            loading.value = true
                            val number = "${phoneNumber.text}"
                            sendVerificationCode(
                                number,
                                mAuth,
                                context as Activity,
                                callbacks
                            )//This is the main method to send the code after verification
                        }
                    }
                }) {
                Text(
                    modifier = Modifier.padding(vertical = 7.dp, horizontal = 15.dp),
                    text = "Sign Up",
                    fontSize = 20.sp,
                    color = colorResource(R.color.white)
                )
            }
            Text(
                modifier = Modifier.clickable { navController.navigate("SignIn") },
                text = "Already have an account",
                color = colorResource(R.color.black)
            )
        }
    }
//    if (loading.value) {
//        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
//    }


    callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            Toast.makeText(context, "Verification successful..", Toast.LENGTH_SHORT).show()
            loading.value = false
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            Toast.makeText(context, "Verification failed.. ${p0.message}", Toast.LENGTH_LONG)
                .show()
            loading.value = false
        }

        override fun onCodeSent(
            verificationId: String,
            p1: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, p1)
            verificationID.value = verificationId
            codeSent.value = true
            loading.value = false
        }
    }
    AnimatedVisibility(visible = codeSent.value) {
        Column {
            TextField(
                enabled = !loading.value,
                value = otp.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { if (it.length <= 6) otp.value = it },
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
                }
            )

            Spacer(modifier = Modifier.height(10.dp))
            val launcherMultiplePermissions = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap ->
                val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (areGranted) {
                    locationRequired = true
                    startLocationUpdates()
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
                }
            }
            Button(
                enabled = !loading.value,
                onClick = {
                    if (TextUtils.isEmpty(otp.value) || otp.value.length < 6) {
                        Toast.makeText(
                            context,
                            "Please enter a valid OTP",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        loading.value = true
                        //This is the main part where we verify the OTP
                        val credential: PhoneAuthCredential =
                            PhoneAuthProvider.getCredential(
                                verificationID.value, otp.value
                            )//Get credential object
                        mAuth.signInWithCredential(credential)
                            .addOnCompleteListener(context as Activity) { task ->
                                if (task.isSuccessful) {
                                    //Code after auth is successful

                                    var api = APIClient.getInstance().create(APIService::class.java)




                                    fusedLocationClient =
                                        LocationServices.getFusedLocationProviderClient(context)
                                    locationCallback = object : LocationCallback() {
                                        override fun onLocationResult(p0: LocationResult) {
                                            for (lo in p0.locations) {
                                                // Update UI with location data
                                                currentLocation = LocationDetails(
                                                    lo.latitude,
                                                    lo.longitude,
                                                    currUserNumber
                                                )
                                            }
                                        }
                                    }


                                    val permissions = arrayOf(
                                        Manifest.permission.ACCESS_COARSE_LOCATION,
                                        Manifest.permission.ACCESS_FINE_LOCATION,


                                        )
//                                    Button(onClick = {
                                    if (permissions.all {
                                            ContextCompat.checkSelfPermission(
                                                context,
                                                it
                                            ) == PackageManager.PERMISSION_GRANTED
                                        }) {
                                        // Get the location
                                        startLocationUpdates()
                                    } else {
                                        launcherMultiplePermissions.launch(permissions)
                                    }
//                                    }) {

//                                    }
                                    Log.d(
                                        "TAG",
                                        "onCreate: ${currentLocation.latitude}  ${currentLocation.longitude}"
                                    )
                                    api.postData(currentLocation)
                                        .enqueue(object : Callback<LocationDetails> {
                                            override fun onResponse(
                                                call: Call<LocationDetails>,
                                                response: Response<LocationDetails>
                                            ) {
                                                if (response.isSuccessful) {

                                                }
                                            }

                                            override fun onFailure(
                                                call: Call<LocationDetails>,
                                                t: Throwable
                                            ) {
                                                Log.d("TAG", "onFailure: ${t.message}")
                                            }

                                        })





                                    StorageManager.findRole(StorageManager.getSavedUser(context)) {


                                        if (it == "Parent") {
                                            navController.navigate("HomeParent") {
                                                popUpTo(navController.graph.id) {
                                                    inclusive = true
                                                }
                                            }
                                        } else {
                                            navController.navigate("Home") {
                                                popUpTo(navController.graph.id) {
                                                    inclusive = true
                                                }
                                            }
                                        }


                                    }
                                } else {
                                    loading.value = false
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
                                            codeSent.value = false
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

private fun sendVerificationCode(
    number: String,
    auth: FirebaseAuth,
    activity: Activity,
    callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
) {
    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber(number)
        .setTimeout(60L, TimeUnit.SECONDS)
        .setActivity(activity)
        .setCallbacks(callbacks)
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}


@SuppressLint("MissingPermission")
private fun startLocationUpdates() {
    locationCallback?.let {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            it,
            Looper.getMainLooper()
        )
    }
}
