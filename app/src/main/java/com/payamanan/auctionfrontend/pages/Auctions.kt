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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.payamanan.auctionfrontend.R
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.sharedComponents.ItemCard
import com.payamanan.auctionfrontend.sharedComponents.ItemToAuction
import com.payamanan.auctionfrontend.ui.theme.bottomBgColor
import com.payamanan.auctionfrontend.ui.theme.goldText
import com.payamanan.auctionfrontend.ui.theme.topBgColor
import com.payamanan.auctionfrontend.viewModels.ItemViewModel

val Interfont = FontFamily(Font(R.font.inter, FontWeight.Normal), Font(R.font.inter18ptbold, FontWeight.Bold))

@Composable
fun Auctions(navController: NavController) {
    val scrollState = rememberScrollState()

    val viewModel: ItemViewModel = viewModel()
    val items by viewModel.items.collectAsState()
    val user = UserSesssion.user

    var showAuctionDialog by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf<Item?>(null) }

    LaunchedEffect(Unit) {
        viewModel.getItems()
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
                text = "Items ready to Auction",
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

            if (items.isEmpty()) {
                Text(
                    text = "No Items found.",
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .align(Alignment.CenterHorizontally)
                )
            } else {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            ItemCard(item = item)
                        }

                        Spacer(Modifier.width(12.dp))

                        Button(
                            onClick = {
                                selectedItem = item
                                showAuctionDialog = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB1822C))
                        ) {
                            Text("Auction", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    if (showAuctionDialog && selectedItem != null) {
        ItemToAuction(
            item = selectedItem!!,
            onDismiss = { showAuctionDialog = false }
        )
    }
}