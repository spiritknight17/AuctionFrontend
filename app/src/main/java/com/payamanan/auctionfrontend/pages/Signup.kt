package com.payamanan.auctionfrontend.pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.payamanan.auctionfrontend.R
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.ui.theme.InriaSerif
import com.payamanan.auctionfrontend.ui.theme.Inter
import com.payamanan.auctionfrontend.viewModels.UserViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState

@Composable
fun Signup(navController: NavController) {
    val userViewModel: UserViewModel = viewModel()
    val userState by userViewModel.userState.collectAsState()

    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    LaunchedEffect(userState) {
        if (userState is ApiState.Success) {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
            userViewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(painter = painterResource(id = R.drawable.chinesecoins), contentDescription = "Chinese Coins Left", modifier = Modifier.size(100.dp))
            Image(painter = painterResource(id = R.drawable.chinesecoins), contentDescription = "Chinese Coins Right", modifier = Modifier.size(100.dp))
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.fallingcoins),
                contentDescription = "Falling Coins",
                modifier = Modifier.size(300.dp)
            )

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .weight(1f)
                    .padding(bottom = 60.dp),
                shape = RoundedCornerShape(40.dp),
                color = Color(0xFF495C26)
            ) {
                Column(
                    modifier = Modifier.padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "Sign Up", style = TextStyle(color = Color.White, fontSize = 36.sp, fontFamily = InriaSerif, fontWeight = FontWeight.Bold))
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Enter Username", style = TextStyle(color = Color(0xFFB1822C), fontSize = 14.sp, fontFamily = Inter))
                        OutlinedTextField(
                            value = username,
                            onValueChange = { username = it },
                            placeholder = { Text("Enter Username....", color = Color(0xFFB1822C).copy(alpha = 0.5f))  },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color(0xFFB1822C),
                                unfocusedTextColor = Color(0xFFB1822C)
                            )
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Enter Email", style = TextStyle(color = Color(0xFFB1822C), fontSize = 14.sp, fontFamily = Inter))
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("Enter Email....", color = Color(0xFFB1822C).copy(alpha = 0.5f)) },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color(0xFFB1822C),
                                unfocusedTextColor = Color(0xFFB1822C)
                            )
                        )
                    }
                    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Text(text = "Enter Password", style = TextStyle(color = Color(0xFFB1822C), fontSize = 14.sp, fontFamily = Inter))
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("Enter Password....", color = Color(0xFFB1822C).copy(alpha = 0.5f)) },
                            visualTransformation = PasswordVisualTransformation(),
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedBorderColor = Color.Transparent,
                                unfocusedBorderColor = Color.Transparent,
                                focusedTextColor = Color(0xFFB1822C),
                                unfocusedTextColor = Color(0xFFB1822C)
                            )
                        )
                    }

                    if (userState is ApiState.Error) {
                        Text(
                            text = (userState as ApiState.Error).message,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { 
                            val newUser = User(
                                userId = null,
                                username = username,
                                email = email,
                                passwordHash = password,
                                role = "USER",
                                status = "ACTIVE"
                            )
                            userViewModel.signup(newUser)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB1822C)),
                        enabled = userState !is ApiState.Loading
                    ) { 
                         if (userState is ApiState.Loading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Sign In", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold) 
                        }
                    }
                    Button(
                        onClick = { navController.navigate("login") },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    ) { Text("Back to Login", color = Color(0xFFB1822C), fontSize = 16.sp, fontWeight = FontWeight.Bold) }
                }
            }
        }
    }
}
