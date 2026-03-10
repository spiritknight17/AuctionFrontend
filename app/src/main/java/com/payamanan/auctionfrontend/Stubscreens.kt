package com.payamanan.auctionfrontend

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.collections.chunked
import kotlin.collections.forEach

private val Interfont = FontFamily(Font(R.font.inter, FontWeight.Normal), Font(R.font.inter18ptbold, FontWeight.Bold))
private val InriaSerif = FontFamily(Font(R.font.inriaserifregular))
private val GoldBtn    = Color(0xFFB8860B)
private val TextDark: Color = Color(0xFF1A1A1A)
private val BgGray: Color = Color(0xFFFBF9F4)
private val HostingBg  = Color(0xFFFDEFD5)
@Composable
fun Favourites(navController: NavController) {
    Column(
        modifier              = Modifier.fillMaxSize(),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Text(text = "Favourites")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

@Composable
fun Auctions(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val topBgColor = Color(0xFFFAF9F6)
    val bottomBgColor = Color(0xFFEAD8BA)
    val goldText = Color(0xFFB1822C)
    val sampleItems = remember {
        mutableStateListOf(
            AuctionItem("tee-t", "Adrian Butiu Limited Tee - Twinnem", "Cotton Tee", "₱500", R.drawable.sample_tee_twinnem, isOwner = false),
            AuctionItem("mug", "Adrian Butiu Limited Mug", "Limited edition.", "₱500", R.drawable.sample_mug, isOwner = false),
            AuctionItem("tee", "Adrian Butiu Limited Tee", "Customized t-shirt.", "₱1,000", R.drawable.sample_tee_wacky, isOwner = false),
            AuctionItem("cal", "Adrian Butiu Calendar", "2024 calendar.", "₱300", R.drawable.sample_calendar, isOwner = false)
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ivy_pfp),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate("account") }
                )
                Spacer(Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = "Welcome", fontFamily = Interfont, fontSize = 14.sp, color = goldText)
                    Text(text = "Ivy Timoteo", fontFamily = Interfont, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = Color(0xFF4B3621))
                }
                // Notification Bell
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, goldText, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_popup_reminder),
                        contentDescription = "Notifications",
                        tint = goldText,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(25.dp))
                    .background(Color.White)
                    .border(0.5.dp, Color.LightGray, RoundedCornerShape(25.dp))
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Outlined.Search, contentDescription = "Search", tint = Color.Gray, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(text = "Search transaction items", fontFamily = Interfont, fontSize = 14.sp, color = Color.LightGray)
                    }
                    BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Icon(painter = painterResource(id = android.R.drawable.ic_menu_sort_by_size), contentDescription = "Filter", tint = Color.Gray, modifier = Modifier.size(20.dp))
            }

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
                color = Color.White,
                modifier = Modifier.clickable { navController.navigate("home") }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "<", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(Modifier.width(8.dp))
                    Text(text = "Return to Home", fontFamily = Interfont, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))

            val biddingItems = sampleItems.filter { !it.isOwner && it.id != "add" }

            biddingItems.forEach { item ->
                TransactionHistoryCard(item = item)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}
@Composable
fun TransactionHistoryCard(item: AuctionItem) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth().fillMaxHeight()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    fontFamily = Interfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Ended 4h 21m 5s ago",
                    fontFamily = Interfont,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFBA3B3C) // Dark Red
                ) {
                    Text(
                        text = "Bought for ${item.price}",
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Image(
                painter = painterResource(id = item.imageRes ?: R.drawable.chinesecoins),
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}
@Composable
fun Notifications(navController: NavController) {
    Column(
        modifier              = Modifier.fillMaxSize(),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Text(text = "Notifications")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}

@Composable
fun AddItem(navController: NavController) {
    Column(
        modifier              = Modifier.fillMaxSize(),
        verticalArrangement   = Arrangement.Center,
        horizontalAlignment   = Alignment.CenterHorizontally
    ) {
        Text(text = "Add Item to Auction")
        Spacer(Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Back")
        }
    }
}