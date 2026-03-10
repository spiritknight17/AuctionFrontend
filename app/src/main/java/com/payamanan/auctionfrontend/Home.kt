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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.payamanan.auctionfrontend.ui.theme.BgGray
import com.payamanan.auctionfrontend.ui.theme.GoldBtn
import com.payamanan.auctionfrontend.ui.theme.HostingBg
import com.payamanan.auctionfrontend.ui.theme.ModalGray
import com.payamanan.auctionfrontend.ui.theme.OliveGreen
import com.payamanan.auctionfrontend.ui.theme.TextDark
import com.payamanan.auctionfrontend.ui.theme.AlertRed
val InriaSerif = FontFamily(Font(R.font.inriaserifregular))
val InterFont  = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter18ptbold, FontWeight.Bold) // Assuming inter_bold based on previous corrections
)

// ── Data model ────────────────────────────────────────────────────────────────
data class AuctionItem(
    val id          : String,
    val title       : String,
    val description : String = "",
    val price       : String,
    val imageRes    : Int? = null,
    val imageUri    : Uri? = null,
    val isOwner     : Boolean = false
)

// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun Home(navController: NavController) {

    val sampleItems = remember {
        mutableStateListOf(
            AuctionItem("add", "", "",                         "",                   null),
            AuctionItem("tee-t", "Adrian Butiu Limited Tee - Twinnem", "Cotton Tee worn by Balong", "₱500",   R.drawable.sample_tee_twinnem, isOwner = true),
            AuctionItem("cal", "Adrian Butiu Calendar",    "2024 calendar.", "₱300",   R.drawable.sample_calendar, isOwner = false),
            AuctionItem("mug", "Adrian Butiu Limited Mug", "Limited edition mug.", "₱500",   R.drawable.sample_mug,      isOwner = false),
            AuctionItem("tee", "Adrian Butiu Limited Tee", "Customized t-shirt.", "₱1,000", R.drawable.sample_tee_wacky, isOwner = false),
            AuctionItem("cal2", "Adrian Butiu Calendar",    "2024 calendar.", "₱300",   R.drawable.sample_calendar, isOwner = false)
        )
    }

    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableIntStateOf(1) } // Default to Home (middle)

    // Dialog states
    var showEditDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEndAuctionDialog by remember { mutableStateOf(false) }
    var selectedItemForDialog by remember { mutableStateOf<AuctionItem?>(null) }

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, start = 16.dp, end = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                BottomNavBar(
                    selectedTab   = selectedTab,
                    onTabSelected = { idx ->
                        selectedTab = idx
                        when (idx) {
                            0 -> navController.navigate("auctions") // History
                            1 -> { /* home */ }
                            2 -> navController.navigate("account")  // User
                        }
                    }
                )
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgGray)
                .padding(bottom = innerPadding.calculateBottomPadding())
                .verticalScroll(scrollState)
        ) {

            // ── Top bar ───────────────────────────────────────────────────────
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = "Welcome",
                        fontFamily = InterFont,
                        fontSize   = 14.sp,
                        color      = GoldBtn
                    )
                    Text(
                        text       = "Ivy Timoteo",
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 22.sp,
                        color      = Color(0xFF4B3621)
                    )
                }
            }

            // ── Payamanan title ───────────────────────────────────────────────
            Text(
                text       = "Payamanan",
                fontFamily = InriaSerif,
                fontWeight = FontWeight.Bold,
                fontSize   = 28.sp,
                color      = GoldBtn,
                modifier   = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(16.dp))

            // ── SECTION: Hosting ─────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HostingBg)
                    .padding(vertical = 24.dp, horizontal = 12.dp)
            ) {
                // Simplified filtering: Just grab owned items and the "add" button
                val hostedItems = sampleItems.filter { it.isOwner || it.id == "add" }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    hostedItems.take(2).forEach { item ->
                        Box(modifier = Modifier.weight(1f)) {
                            if (item.id == "add") {
                                AddItemCard(onClick = { showAddDialog = true })
                            } else {
                                HostedAuctionCard(
                                    item = item,
                                    onEditClick = {
                                        selectedItemForDialog = item
                                        showEditDialog = true
                                    },
                                    onEndAuctionClick = {
                                        selectedItemForDialog = item
                                        showEndAuctionDialog = true
                                    }
                                )
                            }
                        }
                    }
                    if (hostedItems.size == 1) Spacer(Modifier.weight(1f))
                }
            }

            // ── SECTION: Bidding (Popular Auctions) ──────────────────────────
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .background(OliveGreen)
                    .padding(top = 24.dp, bottom = 100.dp, start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text       = "Popular Auctions",
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 22.sp,
                    color      = Color.White,
                    modifier   = Modifier.padding(start = 8.dp, bottom = 16.dp)
                )
                val biddingItems = sampleItems.filter { !it.isOwner && it.id != "add" }
                val rows = biddingItems.chunked(2)
                rows.forEach { rowItems ->
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { item ->
                            Box(modifier = Modifier.weight(1f)) {
                                AuctionCard(
                                    item = item,
                                    onBidClick = { navController.navigate("product-page/${item.id}") }
                                )
                            }
                        }
                        if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
        if (showAddDialog) {
            AuctionFormDialog(
                title = "Add Item to Auction",
                confirmLabel = "Add Item",
                onDismiss = { showAddDialog = false },
                onConfirm = { name, desc, price, uri ->
                    sampleItems.add(
                        AuctionItem(
                            id = "item_${sampleItems.size}",
                            title = name,
                            description = desc,
                            price = "₱$price",
                            imageUri = uri,
                            isOwner = true
                        )
                    )
                    showAddDialog = false
                }
            )
        }

        if (showEditDialog && selectedItemForDialog != null) {
            AuctionFormDialog(
                title = "Edit Item to Auction",
                confirmLabel = "Save Changes",
                initialName = selectedItemForDialog!!.title,
                initialDesc = selectedItemForDialog!!.description,
                initialPrice = selectedItemForDialog!!.price.replace("₱", "").replace(",", ""),
                initialUri = selectedItemForDialog!!.imageUri,
                onDismiss = { showEditDialog = false },
                onConfirm = { name, desc, price, uri ->
                    val index = sampleItems.indexOfFirst { it.id == selectedItemForDialog!!.id }
                    if (index != -1) {
                        sampleItems[index] = selectedItemForDialog!!.copy(
                            title = name,
                            description = desc,
                            price = "₱$price",
                            imageUri = uri
                        )
                    }
                    showEditDialog = false
                }
            )
        }

        if (showEndAuctionDialog && selectedItemForDialog != null) {
            EndAuctionDialog(
                item = selectedItemForDialog!!,
                onDismiss = { showEndAuctionDialog = false },
                onConfirm = {
                    sampleItems.removeIf { it.id == selectedItemForDialog?.id }
                    showEndAuctionDialog = false
                }
            )
        }
    }
}