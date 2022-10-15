package com.example.tomatotrial

sealed class NavigationItem(val route:String){
    object Home:NavigationItem("home")
    object Menu:NavigationItem("menu")
    object Bill:NavigationItem("bill")
}

