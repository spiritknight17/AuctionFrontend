package com.payamanan.auctionfrontend
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
@Composable
fun Login(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welcome to the Login Page")
        Button(onClick = { navController.navigate("home") }) {
            Text(text = "Login")
        }
        Button(onClick = { navController.navigate("signup") }) {
            Text(text = "Sign Up")
        }
    }
}