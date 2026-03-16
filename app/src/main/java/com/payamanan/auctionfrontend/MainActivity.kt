package com.payamanan.auctionfrontend

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.payamanan.auctionfrontend.pages.Account
import com.payamanan.auctionfrontend.pages.Auctions
import com.payamanan.auctionfrontend.pages.Home
import com.payamanan.auctionfrontend.pages.Login
import com.payamanan.auctionfrontend.pages.ProductPage
import com.payamanan.auctionfrontend.pages.Signup
import com.payamanan.auctionfrontend.viewModels.AuctionViewModel

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
    val auctionViewModel: AuctionViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(navController = navController)
        }
        composable("signup") {
            Signup(navController = navController)
        }
        composable("home") {
            Home(
                navController = navController,
                auctionViewModel = auctionViewModel
            )
        }
        composable("account") {
            Account(navController = navController)
        }
        composable("product-page") {
            ProductPage(navController = navController)
        }
        composable(
            route = "product-page/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            ProductPage(
                navController = navController,
                auctionId = id,
            )
        }
        composable("auctions") {
            Auctions(
                navController = navController,
                auctionViewModel = auctionViewModel
            )
        }
    }
}