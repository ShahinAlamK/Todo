package com.iconic.todos.navGraph

sealed class RouteItem(val route:String) {

    data object Login:RouteItem("login_screen")
    data object Register:RouteItem("register_screen")

    data object Home:RouteItem("home_screen")
    data object Profile:RouteItem("profile_screen")

}