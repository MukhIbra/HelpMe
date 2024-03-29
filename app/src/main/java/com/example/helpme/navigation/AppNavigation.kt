package com.example.helpme.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.helpme.screens.SignInScreen
import com.example.helpme.screens.SignUpScreen
import com.example.helpme.screens.HomeScreen
import com.example.helpme.screens.PsychoHelpScreen
import com.example.helpme.screens.SOSScreen
import com.example.helpme.screens.SelfDefenceScreen
import com.example.helpme.screens.SplashScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screens.Splash.route) {
        composable(route = Screens.Splash.route) {
            SplashScreen(navController)
        }
        composable(route = Screens.SignIn.route) {
            SignInScreen(navController)
        }
        composable(route = Screens.SignUp.route) {
            SignUpScreen(navController)
        }
        composable(route = Screens.Home.route) {

            HomeScreen(navController)
        }
        composable(route = Screens.Home.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.SelfDefence.route) {
            SelfDefenceScreen(navController)
        }
        composable(route = Screens.PsychoHelp.route) {
            PsychoHelpScreen(navController)
        }
        composable(route = Screens.HomeParent.route) {
            HomeScreen(navController)
        }
        composable(route = Screens.SOSScreen.route,
            arguments = listOf(
                navArgument("key") {
                    type = NavType.StringType
                }
            )) {navBackStackEntry ->
            val key = navBackStackEntry.arguments?.getStringArrayList("key")
            SOSScreen(navController, key = key!!.toList())
        }
    }
}