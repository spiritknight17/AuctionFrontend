package com.payamanan.auctionfrontend.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.data.model.User
import com.payamanan.auctionfrontend.ui.theme.BorderGray
import com.payamanan.auctionfrontend.ui.theme.GoldYellow
import com.payamanan.auctionfrontend.ui.theme.OffWhite
import com.payamanan.auctionfrontend.ui.theme.OliveGreen
import com.payamanan.auctionfrontend.sharedComponents.ProfileField
import com.payamanan.auctionfrontend.sharedComponents.TransactionHistoryCard
import com.payamanan.auctionfrontend.viewModels.TransactionViewModel
import com.payamanan.auctionfrontend.viewModels.UserViewModel

@Composable
fun Account(navController: NavController, 
            transactionViewModel: TransactionViewModel = viewModel(),
            userViewModel: UserViewModel = viewModel()) {
    val user = UserSesssion.user
    val userState by userViewModel.userState.collectAsState()
    val context = LocalContext.current

    var username by remember { mutableStateOf(user?.username ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var password by remember { mutableStateOf(user?.passwordHash ?: "") }

    val transactions by transactionViewModel.transactions.collectAsState()

    LaunchedEffect(user?.userId) {
        user?.userId?.let { transactionViewModel.getAuctions(it) }
    }

    LaunchedEffect(userState) {
        if (userState is ApiState.Success) {
            Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            userViewModel.resetState()
        } else if (userState is ApiState.Error) {
            Toast.makeText(context, (userState as ApiState.Error).message, Toast.LENGTH_SHORT).show()
            userViewModel.resetState()
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(top = 48.dp, bottom = 32.dp)
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Edit Profile",
                    color = GoldYellow,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, BorderGray, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = GoldYellow,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }

        item {
            ProfileField(
                label = "Username", 
                value = username, 
                onValueChange = { username = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileField(
                label = "Email", 
                value = email, 
                onValueChange = { email = it }, 
                keyboardType = KeyboardType.Email
            )
            Spacer(modifier = Modifier.height(16.dp))
            ProfileField(
                label = "Password", 
                value = password, 
                onValueChange = { password = it }, 
                isPassword = true
            )
            Spacer(modifier = Modifier.height(32.dp))
        }

        item {
            Button(
                onClick = { 
                    val updatedUser = user?.copy(
                        username = username,
                        email = email,
                        passwordHash = password
                    )
                    updatedUser?.let { userViewModel.updateUser(it) }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = OliveGreen),
                enabled = username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && userState !is ApiState.Loading
            ) { 
                if (userState is ApiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Save Changes", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = { 
                    UserSesssion.user = null
                    navController.navigate("login") {
                        popUpTo(0)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8C4F4F))
            ) { Text("Log Out", color = Color.White, fontWeight = FontWeight.Bold) }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                "Transaction History",
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (transactions.isEmpty()) {
            item {
                Text(
                    "No transactions yet.",
                    color = Color.Gray,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
            }
        }

        items(transactions) { transaction ->
            TransactionHistoryCard(transaction = transaction)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
