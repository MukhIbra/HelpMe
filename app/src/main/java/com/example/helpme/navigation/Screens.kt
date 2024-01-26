package com.example.helpme.navigation

sealed class Screens(val route: String) {
    object SignIn : Screens("SignIn")
    object SignUp : Screens("SignUp")
    object Home : Screens("Home")
}