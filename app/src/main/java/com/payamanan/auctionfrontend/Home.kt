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

// ── Colours ───────────────────────────────────────────────────────────────────
private val OliveGreen = Color(0xFF43532D) // Darker green for bidding section
private val HostingBg  = Color(0xFFFDEFD5) // Peach/Beige for hosting
private val GoldBtn    = Color(0xFFB8860B)
private val BgGray     = Color(0xFFFBF9F4)
private val TextDark   = Color(0xFF1A1A1A)
private val AlertRed   = Color(0xFFB22222)
private val SearchBlue = Color(0xFF0091FF)
private val ModalGray  = Color(0xFFF8F8F8)

// ── Fonts ─────────────────────────────────────────────────────────────────────
private val InriaSerif = FontFamily(Font(R.font.inriaserifregular))
private val InterFont  = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter18ptbold,    FontWeight.Bold)
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
    var searchQuery by remember { mutableStateOf("") }
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
                Image(
                    painter = painterResource(id = R.drawable.ivy_pfp),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate("account") }
                )

                Spacer(Modifier.width(16.dp))

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

            // ── Search bar ────────────────────────────────────────────────────
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Search,
                    contentDescription = "Search",
                    tint               = Color.Gray,
                    modifier           = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier.weight(1f)) {
                    if (searchQuery.isEmpty()) {
                        Text(
                            text       = "Search for auction item",
                            fontFamily = InterFont,
                            fontSize   = 14.sp,
                            color      = Color(0xFFAAAAAA)
                        )
                    }
                    BasicTextField(
                        value         = searchQuery,
                        onValueChange = { searchQuery = it },
                        singleLine    = true,
                        textStyle     = TextStyle(
                            fontFamily = InterFont,
                            fontSize   = 14.sp,
                            color      = TextDark
                        ),
                        modifier      = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

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
                val filteredItems = sampleItems.filter { item ->
                    item.title.contains(searchQuery, ignoreCase = true) || 
                    (item.id == "add" && searchQuery.isEmpty())
                }
                val hostedItems = filteredItems.filter { it.isOwner || it.id == "add" }
                
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
                    .filter { it.title.contains(searchQuery, ignoreCase = true) }
                
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

        // ── Dialogs ──────────────────────────────────────────────────────────
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

@Composable
fun AuctionFormDialog(
    title: String,
    confirmLabel: String,
    initialName: String = "",
    initialDesc: String = "",
    initialPrice: String = "",
    initialUri: Uri? = null,
    onDismiss: () -> Unit,
    onConfirm: (String, String, String, Uri?) -> Unit
) {
    var name by remember { mutableStateOf(initialName) }
    var desc by remember { mutableStateOf(initialDesc) }
    var price by remember { mutableStateOf(initialPrice) }
    var imageUri by remember { mutableStateOf(initialUri) }

    val pickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) imageUri = uri
    }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .wrapContentHeight(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = ModalGray)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(Modifier.height(20.dp))

                // Image Upload Area
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFFD9D9D9))
                        .clickable { pickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (imageUri != null) {
                        AsyncImage(
                            model = imageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AddAPhoto,
                            contentDescription = "Add Photo",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(Modifier.height(24.dp))

                DialogTextField(label = "Item Name", value = name, onValueChange = { name = it })
                Spacer(Modifier.height(16.dp))
                DialogTextField(label = "Item Description", value = desc, onValueChange = { desc = it })
                Spacer(Modifier.height(16.dp))
                DialogTextField(label = "Initial Price", value = price, onValueChange = { price = it })

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = { onConfirm(name, desc, price, imageUri) },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldBtn)
                ) {
                    Text(text = confirmLabel, color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun DialogTextField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp, bottom = 4.dp))
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, RoundedCornerShape(12.dp))
                .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(12.dp))
                .padding(14.dp),
            textStyle = TextStyle(fontSize = 14.sp, color = TextDark)
        )
    }
}

@Composable
fun EndAuctionDialog(item: AuctionItem, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("End Auction?", fontWeight = FontWeight.Bold) },
        text = { Text("Are you sure you want to end the auction? You will receive ${item.price}. This action cannot be undone.") },
        confirmButton = {
            Button(onClick = onConfirm, colors = ButtonDefaults.buttonColors(containerColor = AlertRed) ) {
                Text("End Auction")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel", color = Color.Gray) }
        }
    )
}

@Composable
private fun AddItemCard(onClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth().height(170.dp),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier         = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, GoldBtn, CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "Add", tint = GoldBtn)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Add Item\nto\nAuction",
                fontFamily = InterFont,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

@Composable
private fun HostedAuctionCard(item: AuctionItem, onEditClick: () -> Unit, onEndAuctionClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(170.dp).clickable { onEditClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize().padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1.1f)) {
                Text(text = item.title, fontFamily = InterFont, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = TextDark, maxLines = 3, overflow = TextOverflow.Ellipsis, lineHeight = 16.sp)
                Text(text = item.price, fontFamily = InterFont, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = Color(0xFF4E5B2E))
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = { onEndAuctionClick() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = AlertRed),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(text = "Close Bid", fontSize = 10.sp, color = Color.White)
                    }
                    Button(
                        onClick = { onEditClick() },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldBtn),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(text = "Edit", fontSize = 10.sp, color = Color.White)
                    }
                }
            }
            
            val imageModifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
            if (item.imageUri != null) {
                AsyncImage(
                    model = item.imageUri,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = imageModifier
                )
            } else if (item.imageRes != null) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    contentScale = ContentScale.Fit,
                    modifier = imageModifier
                )
            }
        }
    }
}

@Composable
private fun AuctionCard(item: AuctionItem, onBidClick: () -> Unit) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            Box(modifier = Modifier.fillMaxWidth().height(150.dp), contentAlignment = Alignment.Center) {
                if (item.imageUri != null) {
                    AsyncImage(
                        model = item.imageUri,
                        contentDescription = item.title,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else if (item.imageRes != null) {
                    Image(
                        painter            = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        contentScale       = ContentScale.Fit,
                        modifier           = Modifier.fillMaxSize().padding(16.dp)
                    )
                }
            }
            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = item.title, fontFamily = InterFont, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(Modifier.height(4.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text(text = item.price, fontFamily = InterFont, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextDark)
                    Button(
                        onClick = onBidClick,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = GoldBtn),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 0.dp),
                        modifier = Modifier.height(30.dp)
                    ) {
                        Text(text = "Place a Bid", fontSize = 11.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun BottomNavBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().height(64.dp),
        shape = RoundedCornerShape(32.dp),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val items = listOf(
                Icons.Outlined.History to 0, // Transaction History
                Icons.Outlined.Home to 1,
                Icons.Outlined.Person to 2 // User
            )
            items.forEach { (icon, index) ->
                IconButton(onClick = { onTabSelected(index) }) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (selectedTab == index) GoldBtn else Color(0xFF4B3621),
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        }
    }
}
