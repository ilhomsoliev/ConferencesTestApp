package com.ilhomsoliev.conferencestestapp.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilhomsoliev.conferencestestapp.core.Constants

@Composable
fun Navigation() {
    val navController = rememberNavController()

    Scaffold {
        NavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = Screens.MainScreen.route
        ) {
            composable(route = Screens.MainScreen.route) {

            }
            composable(
                route = Screens.WebViewScreen.route, arguments = listOf(
                    navArgument(Constants.URL_ARG_ID) {
                        type = NavType.StringType
                    })
            ) { backStackEntry ->

            }
        }
    }
}