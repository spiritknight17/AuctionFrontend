package com.payamanan.auctionfrontend
import android.R.attr.label
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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

@Composable
fun Login(navController: NavController) {
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
                    Text(text = "Username/Email", style = TextStyle(color = Color(0xFFB1822C), fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal, textAlign = TextAlign.Left))
                    var username by remember { mutableStateOf("") }
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        placeholder = { Text("Enter Username/Email...") },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF495C26),
                            unfocusedBorderColor = Color(0xFF495C26),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFFB1822C80),
                            unfocusedTextColor = Color(0xFFB1822C80),
                            focusedLabelColor = Color(0xFFB1822C)
                        )
                    )
                    Text(text = "Password", style = TextStyle(color = Color(0xFFB1822C), fontSize = 16.sp, fontFamily = Inter, fontWeight = FontWeight.Normal, textAlign = TextAlign.Left))
                    var password by remember { mutableStateOf("") }
                    OutlinedTextField(

                        value = password,
                        onValueChange = { password = it },
                        placeholder = { Text("Enter Password...") },
                        visualTransformation = PasswordVisualTransformation(),
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color(0xFF495C26),
                            unfocusedBorderColor = Color(0xFF495C26),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedTextColor = Color(0xFFB1822C80),
                            unfocusedTextColor = Color(0xFFB1822C80),
                            focusedLabelColor = Color(0xFFB1822C)
                        )
                    )
                    Button(
                        onClick = { navController.navigate("home") },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(15.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF495C26))
                    ) { Text("Login") }

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