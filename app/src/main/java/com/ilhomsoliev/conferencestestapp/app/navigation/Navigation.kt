package com.ilhomsoliev.conferencestestapp.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilhomsoliev.conferencestestapp.presentation.main.MainEvent
import com.ilhomsoliev.conferencestestapp.presentation.main.MainScreen
import com.ilhomsoliev.conferencestestapp.presentation.main.MainViewModel
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
                val viewModel = hiltViewModel<MainViewModel>()
                val state = viewModel.state.collectAsState().value
                MainScreen(state, onEvent = { event ->
                    when (event) {
                        is MainEvent.OnItemClick -> {
                            navController.navigate(
                                Screens.WebViewScreen.route.replace(
                                    "{${Constants.URL_ARG_ID}}",
                                    event.url.removePrefix("/guide/")
                                )
                            )
                        }
                    }
                })
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