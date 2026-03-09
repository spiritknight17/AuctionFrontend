package com.payamanan.auctionfrontend

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage

private val OliveGreen = Color(0xFF4A5C2F)
private val GoldYellow = Color(0xFFC8962A)
private val OffWhite   = Color(0xFFF5F5F0)
private val DarkText   = Color(0xFF1A1A1A)
private val SubtleGray = Color(0xFF888888)
private val BorderGray = Color(0xFFDDDDDD)

@Composable
fun Account(navController: NavController) {

    var fullName        by remember { mutableStateOf("Ivy Timoteo") }
    var username        by remember { mutableStateOf("nirpt") }
    var email           by remember { mutableStateOf("ivytimoteo@hotmail.com") }
    var currentPassword by remember { mutableStateOf("") }
    var newPassword     by remember { mutableStateOf("") }

    // Holds the URI of the photo the user picked — null means show the default drawable
    var profileImageUri by remember { mutableStateOf<Uri?>(null) }

    // Launches the system photo picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) profileImageUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
            .padding(top = 48.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ── Header ────────────────────────────────────────────────────────────
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

        // ── Profile picture with camera icon overlay ──────────────────────────
        Box(
            modifier = Modifier
                .size(110.dp)
                .clickable { photoPickerLauncher.launch("image/*") },
            contentAlignment = Alignment.BottomEnd
        ) {
            // Show picked photo if available, otherwise show default drawable
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ivy_pfp),
                    contentDescription = "Profile Picture",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            }

            // Camera icon badge at bottom-right
            Box(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape)
                    .background(OliveGreen)
                    .border(2.dp, OffWhite, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Change Photo",
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── Form fields ───────────────────────────────────────────────────────
        ProfileField(label = "Full Name", value = fullName, onValueChange = { fullName = it })

        Spacer(modifier = Modifier.height(16.dp))

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

        Spacer(modifier = Modifier.height(16.dp))

        ProfileField(
            label = "New Password",
            value = newPassword,
            onValueChange = { newPassword = it },
            isPassword = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // ── Save Changes button ───────────────────────────────────────────────
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

@Composable
private fun ProfileField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = SubtleGray,
            fontSize = 12.sp,
            fontWeight = FontWeight.Normal
        )

        Spacer(modifier = Modifier.height(6.dp))

        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = OliveGreen,
                unfocusedBorderColor = BorderGray,
                focusedTextColor = DarkText,
                unfocusedTextColor = DarkText,
                cursorColor = OliveGreen,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
    }
}