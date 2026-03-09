package com.payamanan.auctionfrontend
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
@Composable
fun Home(navController: NavController) {
    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
        Text(text = "Welcome to the Home Page")
        Button(onClick = { navController.navigate("account") }) {
            Text(text = "Account")
        }
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Home")
        }
        Button(onClick = { navController.navigate("product-page") }) {
            Text(text = "Product Page")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text(text = "Log Out")
        }
    }
}