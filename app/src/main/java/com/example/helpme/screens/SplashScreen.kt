package com.example.helpme.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.helpme.R
import com.example.helpme.StorageManager
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    LaunchedEffect(true) {

        delay(3000)
        Log.d("TAG", "SplashScreen: ${StorageManager.getSavedUser(context)}")
        if (StorageManager.getSavedUser(context) == "") {
            navController.navigate("SignIn") {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        } else {

            StorageManager.findRole(StorageManager.getSavedUser(context)) {
                Log.d("TAG", "SplashScreen: ${it}")
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

        }
    }


//    val imageLoader = ImageLoader.Builder(LocalContext.current)
//        .components {
//            if (SDK_INT >= 28) {
//                add(ImageDecoderDecoder.Factory())
//            } else {
//                add(GifDecoder.Factory())
//            }
//        }
//        .build()

    Image(
        painter = rememberAsyncImagePainter(R.drawable.logo),
        contentDescription = null,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )

}