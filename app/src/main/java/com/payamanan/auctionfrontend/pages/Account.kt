package com.payamanan.auctionfrontend.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.ui.theme.BorderGray
import com.payamanan.auctionfrontend.ui.theme.GoldYellow
import com.payamanan.auctionfrontend.ui.theme.OffWhite
import com.payamanan.auctionfrontend.ui.theme.OliveGreen
import com.payamanan.auctionfrontend.sharedComponents.ProfileField

@Composable
fun Account(navController: NavController) {
    val user = UserSesssion.user
    var username by remember { mutableStateOf(user?.username ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var currentPassword by remember { mutableStateOf(user?.passwordHash ?: "") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

        Spacer(modifier = Modifier.height(28.dp))

        Spacer(modifier = Modifier.height(32.dp))


        ProfileField(label = "Username", value = username, onValueChange = { username = it })

        Spacer(modifier = Modifier.height(16.dp))

        ProfileField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProfileField(
            label = "Current Password",
            value = currentPassword,
            onValueChange = { currentPassword = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* TODO: save changes */ },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = OliveGreen)
        ) {
            Text(
                text = "Save Changes",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
