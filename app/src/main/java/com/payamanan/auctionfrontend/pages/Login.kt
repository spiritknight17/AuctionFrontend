package com.payamanan.auctionfrontend.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.payamanan.auctionfrontend.ui.theme.InriaSerif
import com.payamanan.auctionfrontend.ui.theme.Inter
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.lifecycle.viewmodel.compose.viewModel
import com.payamanan.auctionfrontend.R
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.viewModels.UserViewModel
import android.util.Patterns

@Composable
fun Login(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    val userState by userViewModel.userState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Custom state to hold validation/API error messages
    var localError by remember { mutableStateOf("") }

    LaunchedEffect(userState) {
        when (userState) {
            is ApiState.Success -> {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
                userViewModel.resetState()
            }
            is ApiState.Error -> {
                // Override standard HTTP exception messages with a custom user-friendly error
                localError = "Invalid email or password."
                userViewModel.resetState()
            }
            else -> {}
        }
    }

    Surface( modifier = Modifier.fillMaxSize(), color = Color(0xFF495C26)){
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.height(40.dp))
            Image(painter = painterResource(id = R.drawable.fallingcoins), contentDescription = "Falling Coins", modifier = Modifier.size(300.dp))
            Text(text = "Payamanan", style = TextStyle(color = Color.White, fontSize = 40.sp, fontFamily = InriaSerif, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center))
            Text(text = "Auction Application", style = TextStyle(color = Color.White, fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal, textAlign = TextAlign.Center))
            Spacer(modifier = Modifier.height(25.dp))
            Surface(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStart = 75.dp, topEnd = 75.dp),
                color = Color.White
            ) {
                Column(modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {

                    Text(text = "Login", modifier = Modifier.align(Alignment.CenterHorizontally), style = TextStyle(color = Color(0xFFB1822C), fontSize = 40.sp, fontFamily = InriaSerif, fontWeight = FontWeight.Bold))

                    Text(text = "Email", style = TextStyle(color = Color(0xFFB1822C), fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal, textAlign = TextAlign.Left))

                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            localError = "" // Clear error as user types
                        },
                        placeholder = { Text("Enter Email...", color = Color(0xFFB1822C).copy(alpha = 0.5f)) },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF495C26),
                            unfocusedBorderColor = Color(0xFF495C26),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFFB1822C),
                            unfocusedTextColor = Color(0xFFB1822C),
                            focusedLabelColor = Color(0xFFB1822C)
                        )
                    )

                    Text(text = "Password", style = TextStyle(color = Color(0xFFB1822C), fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal, textAlign = TextAlign.Left))

                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            localError = "" // Clear error as user types
                        },
                        placeholder = { Text("Enter Password...", color = Color(0xFFB1822C).copy(alpha = 0.5f)) },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF495C26),
                            unfocusedBorderColor = Color(0xFF495C26),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFFB1822C),
                            unfocusedTextColor = Color(0xFFB1822C),
                            focusedLabelColor = Color(0xFFB1822C)
                        )
                    )

                    // Display our custom local error message
                    if (localError.isNotEmpty()) {
                        Text(
                            text = localError,
                            color = Color.Red,
                            fontSize = 14.sp,
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            textAlign = TextAlign.Center
                        )
                    }

                    Button(
                        onClick = {
                            if (email.isBlank() || password.isBlank()) {
                                localError = "Please fill in all fields."
                                return@Button
                            }

                            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                localError = "Please enter a valid email address."
                                return@Button
                            }

                            val user = User(
                                userId = null,
                                username = null,
                                email = email,
                                passwordHash = password,
                                role = null,
                                status = null
                            )
                            userViewModel.login(user)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495C26)),
                        enabled = userState !is ApiState.Loading
                    ) {
                        if (userState is ApiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Login")
                        }
                    }

                    Button(
                        onClick = { navController.navigate("signup") },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = BorderStroke(2.dp, Color(0xFF495C26))
                    ) { Text("Sign Up", color = Color(0xFFB1822C)) }
                }
            }
        }
    }
}
