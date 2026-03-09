package com.payamanan.auctionfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(navController = navController)
        }
        composable("signup") {
            Signup(navController = navController)
        }
        composable("home") {
            Home(navController = navController)
        }
        composable("account") {
            Account(navController = navController)
        }
        composable("product-page") {
            ProductPage(navController = navController)
        }
        composable("product-page/{id}") {
            ProductPage(navController = navController)
        }
        composable("favourites") {
            Favourites(navController = navController)
        }
        composable("auctions") {
            Auctions(navController = navController)
        }
        composable("notifications") {
            Notifications(navController = navController)
        }
        composable("add-item") {
            AddItem(navController = navController)
        }
    }
}