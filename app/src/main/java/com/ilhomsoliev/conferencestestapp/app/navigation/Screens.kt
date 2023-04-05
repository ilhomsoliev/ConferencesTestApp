package com.ilhomsoliev.conferencestestapp.app.navigation

import com.ilhomsoliev.conferencestestapp.core.Constants


sealed class Screens(val route: String) {
    object MainScreen : Screens("MainScreen")
    object WebViewScreen : Screens("WebViewScreen/{${Constants.URL_ARG_ID}}")
}

