package com.payamanan.auctionfrontend.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.payamanan.auctionfrontend.data.ApiState
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.data.model.BidRequest
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.dialogs.AuctionFormDialog
import com.payamanan.auctionfrontend.dialogs.EndAuctionDialog
import com.payamanan.auctionfrontend.sharedComponents.AddItemCard
import com.payamanan.auctionfrontend.sharedComponents.AuctionCard
import com.payamanan.auctionfrontend.sharedComponents.BottomNavBar
import com.payamanan.auctionfrontend.sharedComponents.HostedAuctionCard
import com.payamanan.auctionfrontend.ui.theme.BgGray
import com.payamanan.auctionfrontend.ui.theme.GoldBtn
import com.payamanan.auctionfrontend.ui.theme.HostingBg
import com.payamanan.auctionfrontend.ui.theme.OliveGreen
import com.payamanan.auctionfrontend.viewModels.AuctionViewModel
import com.payamanan.auctionfrontend.viewModels.ItemViewModel
import java.util.Date


val InriaSerif = FontFamily(Font(R.font.inriaserifregular))
val InterFont  = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter18ptbold, FontWeight.Bold)
)

@Composable
fun Home(navController: NavController) {
    val user = UserSesssion.user
    val auctionViewModel: AuctionViewModel = viewModel()
    val itemViewModel: ItemViewModel = viewModel()
    val items by itemViewModel.items.collectAsState()
    val auctions by auctionViewModel.auctions.collectAsState()
    val itemState by itemViewModel.itemState.collectAsState()
    

    var showEditDialog by remember { mutableStateOf(false) }
    var showAddDialog by remember { mutableStateOf(false) }
    var showEndAuctionDialog by remember { mutableStateOf(false) }
    var selectedItemForDialog by remember { mutableStateOf<Item?>(null) }
    var selectedAuctionForDialog by remember { mutableStateOf<Auction?>(null) }
    val scrollState = rememberScrollState()
    var selectedTab by remember { mutableIntStateOf(1) }
    LaunchedEffect(itemState) {
        if (itemState is ApiState.Success) {
            itemViewModel.getItems()
            itemViewModel.resetState()
            showAddDialog = false
        }
    }

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
                    selectedTab = selectedTab,
                    onTabSelected = { idx ->
                        selectedTab = idx
                        when (idx) {
                            0 -> navController.navigate("auctions")
                            1 -> { /* home */
                            }

                            2 -> navController.navigate("account")
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
                        text       = user?.username ?: "Guest",
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 22.sp,
                        color      = Color(0xFF4B3621)
                    )
                }
            }


            Text(
                text       = "Payamanan",
                fontFamily = InriaSerif,
                fontWeight = FontWeight.Bold,
                fontSize   = 28.sp,
                color      = GoldBtn,
                modifier   = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(16.dp))


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HostingBg)
                    .padding(vertical = 24.dp, horizontal = 12.dp)
            ) {

                val hostedAuctions = auctions.filter { it.item.seller.userId == user?.userId }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.Red)
                    ) {
                        AddItemCard(onClick = {
                            selectedItemForDialog = Item(
                                id = null,
                                name = "",
                                description = "",
                                seller = user!!
                            )
                            showAddDialog = true
                        })
                    }
                    
                    hostedAuctions.take(1).forEach { auction ->
                        Box(modifier = Modifier.weight(1f)) {
                            HostedAuctionCard(
                                auction = auction,
                                onEditClick = {
                                    selectedAuctionForDialog = auction
                                    showEditDialog = true
                                },
                                onEndAuctionClick = {
                                    selectedAuctionForDialog = auction
                                    showEndAuctionDialog = true
                                }
                            )
                        }
                    }
                    if (hostedAuctions.isEmpty()) Spacer(Modifier.weight(1f))
                }
            }

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
                val biddingAuctions = auctions.filter { it.item.seller.userId != user?.userId }
                val rows = biddingAuctions.chunked(2)
                rows.forEach { rowItems ->
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { auction ->
                            Box(modifier = Modifier.weight(1f)) {
                                AuctionCard(
                                    auction = auction,
                                    onBidClick = { navController.navigate("product-page/${auction.id}") }
                                )
                            }
                        }
                        if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                    }
                    Spacer(Modifier.height(16.dp))
                }
            }
        }

        if (showAddDialog && selectedItemForDialog != null) {
            AuctionFormDialog(
                item = selectedItemForDialog!!,
                onDismiss = { showAddDialog = false },
                onConfirm = { newItem ->
                    itemViewModel.createItem(newItem)
                }
            )
        }

        /*if (showEditDialog && selectedAuctionForDialog != null) {
            AuctionFormDialog( // Ensure this still exists or update it to ItemFormDialog too
                auction = selectedAuctionForDialog!!,
                onDismiss = { showEditDialog = false },
                onConfirm = { updatedAuction ->
                    auctionViewModel.createAuction(updatedAuction)
                    showEditDialog = false
                }
            )
        }*/

        if (showEndAuctionDialog && selectedAuctionForDialog != null) {
            EndAuctionDialog(
                auction = selectedAuctionForDialog!!,
                onDismiss = { showEndAuctionDialog = false },
                onConfirm = {
                    showEndAuctionDialog = false
                }
            )
        }
    }
}
