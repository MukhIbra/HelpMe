package com.example.helpme.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.helpme.ChatViewModel
import com.example.helpme.R
import com.example.helpme.StorageManager
import com.example.helpme.data.LocationDetails
import com.example.joshqinshop.networking.APIClient
import com.example.joshqinshop.networking.APIService
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun HomeScreen(navController: NavController) {
    var context = LocalContext.current

    var currUserNumber = StorageManager.getSavedUser(context = context)
    var currentLocation by remember {
        mutableStateOf(LocationDetails(0.toDouble(), 0.toDouble(), currUserNumber))
    }

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




    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 20.dp)
                        .clickable { navController.navigate("TalkingPoints") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_talking),
                        contentDescription = "Talking Points"
                    )
                    Text("Talking Points", fontSize = 22.sp)
                }
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                        .clickable { navController.navigate("PsychoHelp") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_psycho_help),
                        contentDescription = "Psycho Help"
                    )
                    Text("Psycho Help", fontSize = 22.sp)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 47.dp, vertical = 20.dp)
                        .clickable {
                            var api = APIClient
                                .getInstance()
                                .create(APIService::class.java)
                            api
                                .getNearbyUsers(StorageManager.getSavedUser(context))
                                .enqueue(object : Callback<List<String>> {
                                    override fun onResponse(
                                        call: Call<List<String>>,
                                        response: Response<List<String>>
                                    ) {
                                        if (response.isSuccessful) {
//                                            var key = response.body()!!
//                                            navController.navigate("SOS/${key}")
                                            Log.d("TAG", "onResponse: ${response.body()!!.joinToString () }")
                                        }
                                    }

                                    override fun onFailure(call: Call<List<String>>, t: Throwable) {
                                        TODO("Not yet implemented")
                                    }

                                })

                        },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_sos),
                        contentDescription = "SOS"
                    )
                    Text("SOS", fontSize = 22.sp)
                }
                Column(
                    modifier = Modifier
                        .background(
                            colorResource(R.color.yellow), RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 22.dp, vertical = 20.dp)
                        .clickable { navController.navigate("SelfDefence") },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        painter = painterResource(R.drawable.ic_defence),
                        contentDescription = "Self Defence"
                    )
                    Text("Self Defence", fontSize = 22.sp)
                }
            }
        }
    }
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
