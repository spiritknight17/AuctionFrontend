package com.payamanan.auctionfrontend.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.payamanan.auctionfrontend.R
import com.payamanan.auctionfrontend.sharedComponents.TransactionHistoryCard

val Interfont = FontFamily(Font(R.font.inter, FontWeight.Normal), Font(R.font.inter18ptbold, FontWeight.Bold))

@Composable
fun Auctions(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val topBgColor = Color(0xFFFAF9F6)
    val bottomBgColor = Color(0xFFEAD8BA)
    val goldText = Color(0xFFB1822C)
    val sampleItems = remember {
        mutableStateListOf(
            AuctionItem(
                "tee-t",
                "Adrian Butiu Limited Tee - Twinnem",
                "Cotton Tee",
                "₱500",
                R.drawable.sample_tee_twinnem,
                isOwner = false
            ),
            AuctionItem(
                "mug",
                "Adrian Butiu Limited Mug",
                "Limited edition.",
                "₱500",
                R.drawable.sample_mug,
                isOwner = false
            ),
            AuctionItem(
                "tee",
                "Adrian Butiu Limited Tee",
                "Customized t-shirt.",
                "₱1,000",
                R.drawable.sample_tee_wacky,
                isOwner = false
            ),
            AuctionItem(
                "cal",
                "Adrian Butiu Calendar",
                "2024 calendar.",
                "₱300",
                R.drawable.sample_calendar,
                isOwner = false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(bottomBgColor)
            .verticalScroll(scrollState)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(topBgColor)
                .padding(bottom = 24.dp)
        ) {

            Spacer(Modifier.height(24.dp))
            Text(
                text = "Transaction History",
                fontFamily = InriaSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = goldText,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = Color.Transparent,
                modifier = Modifier.clickable { navController.navigate("home") }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .border(1.5.dp, Color.White, CircleShape)
                            .clickable { navController.popBackStack() },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color(0xFFB1822C),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Replaced filteredItems with sampleItems directly
            if (sampleItems.isEmpty()) {
                Text(
                    text = "No transactions found.",
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 20.dp).align(Alignment.CenterHorizontally)
                )
            } else {
                sampleItems.forEach { item ->
                    TransactionHistoryCard(item = item)
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}