package com.iconic.todos.navGraph

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iconic.todos.ui.screens.account.ProfileScreen
import com.iconic.todos.ui.screens.create.CreateScreen
import com.iconic.todos.ui.screens.home.HomeScreen
import com.iconic.todos.ui.screens.login.LoginScreen
import com.iconic.todos.ui.screens.login.LoginViewModel
import com.iconic.todos.ui.screens.register.RegisterScreen
import com.iconic.todos.ui.screens.update.UpdateScreen

@Composable
fun Routes(
    loginViewModel: LoginViewModel = viewModel()
) {

    val startDestination =
        if (loginViewModel.isLoggedIn) RouteItem.Home.route else RouteItem.Login.route

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable(RouteItem.Login.route) {
            LoginScreen(
                navigateToHome = {
                    navController.navigate(RouteItem.Home.route) {
                        popUpTo(
                            RouteItem.Login.route
                        ) {
                            inclusive = true
                        }
                    }
                },
                navigateToRegister = { navController.navigate(RouteItem.Register.route) }
            )
        }
        composable(RouteItem.Register.route) {
            RegisterScreen(
                navigateToHome = {
                    navController.navigate(RouteItem.Home.route) {
                        popUpTo(
                            RouteItem.Register.route
                        ) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(RouteItem.Home.route) {
            HomeScreen(
                navigateToProfile = {
                    navController.navigate(RouteItem.Profile.route)
                },
                navigateToUpdate = {
                    navController.navigate("${RouteItem.Update.route}/$it")
                },
                navigateToCreate = {
                    navController.navigate(RouteItem.Create.route)
                }
            )
        }
        composable(RouteItem.Create.route) {
            CreateScreen(
                navigateToBack = { navController.popBackStack() }
            )
        }

        composable("${RouteItem.Update.route}/{id}",
        ) {
            UpdateScreen(
                navigateToBack = { navController.popBackStack() }
            )
        }


        composable(RouteItem.Profile.route) {
            ProfileScreen(
                logOut = {
                    navController.navigate(RouteItem.Login.route) {
                        popUpTo(
                            RouteItem.Profile.route
                        ) {
                            inclusive = true
                        }
                    }
                },
                navigateToBack = { navController.popBackStack() })
        }
    }
}