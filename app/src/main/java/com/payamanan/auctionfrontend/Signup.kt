package com.payamanan.auctionfrontend
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color

@Composable
fun Signup(navController: NavController) {
    Surface( modifier = Modifier.fillMaxSize(), color = Color(0xFFFFFFFF)) {
        Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Welcome to the Signup Page")
            Button(onClick = { navController.popBackStack() }) {
                Text(text = "Go Back to Login")
            }
        }
    }

}