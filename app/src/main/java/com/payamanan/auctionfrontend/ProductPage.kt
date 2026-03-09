package com.payamanan.auctionfrontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign

private val OliveGreen = Color(0xFF4A5C2F)
private val GoldYellow = Color(0xFFC8962A)
private val OffWhite   = Color(0xFFF5F5F0)
private val LightGray  = Color(0xFFDDDDDD)
private val DarkText   = Color(0xFF1A1A1A)
private val SubtleGray = Color(0xFF999999)

@Composable
fun ProductPage(navController: NavController) {

    var bidAmount  by remember { mutableStateOf(1_000) }
    val stepAmount = 500
    val minBid     = 1_000

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OliveGreen)
    ) {

        // GREEN SECTION
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp)
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Timer row
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Time Left",
                        color = OffWhite.copy(alpha = 0.80f),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal
                    )
                    Text(
                        text = "04:21:04",
                        color = GoldYellow,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(modifier = Modifier.align(Alignment.CenterStart)) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(OffWhite.copy(alpha = 0.85f)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = { navController.popBackStack() },
                            modifier = Modifier.size(38.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = DarkText,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Product image — fixed size, change drawable name as needed
            Image(
                painter = painterResource(id = R.drawable.sample_tee_wacky),
                contentDescription = "Product Image",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(width = 300.dp, height = 250.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Adrian Butiu Limited Tee",
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Medium Sized Customized T-Shirt Made from Cotton",
                color = OffWhite.copy(alpha = 0.65f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            // Current highest bid card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .border(1.dp, OffWhite.copy(alpha = 0.25f), RoundedCornerShape(14.dp))
                    .background(Color(0xFF3D4F28))
                    .padding(vertical = 14.dp, horizontal = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Current Highest Bid",
                        color = OffWhite.copy(alpha = 0.70f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "₱7,000.00",
                        color = GoldYellow,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        text = "Jamie del Rosario",
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        // WHITE BOTTOM SHEET
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.31f)
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(OffWhite)
                .padding(horizontal = 24.dp)
                .padding(top = 12.dp, bottom = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .width(44.dp).height(4.dp)
                    .clip(CircleShape)
                    .background(LightGray)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Place a Bid",
                color = DarkText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Initial Price: ₱1,000.00",
                color = SubtleGray,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Stepper row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            if (bidAmount - stepAmount >= minBid) bidAmount -= stepAmount
                        },
                        modifier = Modifier.size(46.dp)
                    ) {
                        Text("−", fontSize = 26.sp, color = DarkText, fontWeight = FontWeight.Light)
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.5.dp, GoldYellow, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "₱${"%,d".format(bidAmount)}.00",
                        color = GoldYellow,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, LightGray, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = { bidAmount += stepAmount },
                        modifier = Modifier.size(46.dp)
                    ) {
                        Text("+", fontSize = 26.sp, color = DarkText, fontWeight = FontWeight.Light)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit button
            Button(
                onClick = { /* TODO: wire to ViewModel */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow)
            ) {
                Text(
                    text = "₱${"%,d".format(bidAmount)}.00",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(16.dp))

                Box(
                    modifier = Modifier
                        .width(1.dp)
                        .height(24.dp)
                        .background(Color.White.copy(alpha = 0.55f))
                )

                Spacer(modifier = Modifier.width(16.dp))

                Text(
                    text = "Submit Bid",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}