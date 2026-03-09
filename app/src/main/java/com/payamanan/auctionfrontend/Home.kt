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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.MoreVert
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
import androidx.navigation.NavController

// ── Colours ───────────────────────────────────────────────────────────────────
private val OliveGreen = Color(0xFF4E5B2E)
private val GoldBtn    = Color(0xFF8B7355)
private val BgGray     = Color(0xFFF5F5F0)
private val TextDark   = Color(0xFF1A1A1A)
private val HeartRed   = Color(0xFFD32F2F)

// ── Fonts ─────────────────────────────────────────────────────────────────────
// Place inria_serif_regular.ttf, inter_regular.ttf, inter_bold.ttf in res/font/
private val InriaSerif = FontFamily(Font(R.font.inriaserifregular))
private val InterFont  = FontFamily(
    Font(R.font.inter, FontWeight.Normal),
    Font(R.font.inter18ptbold,    FontWeight.Bold)
)

// ── Data model ────────────────────────────────────────────────────────────────
data class AuctionItem(
    val id          : String,
    val title       : String,
    val price       : String,
    val timeLeft    : String,
    val imageRes    : Int?,
    val isFavourite : Boolean = false
)

// ── Screen ────────────────────────────────────────────────────────────────────
@Composable
fun Home(navController: NavController) {

    val sampleItems = remember {
        mutableStateListOf(
            AuctionItem("add", "",                          "",        "",            null),
            AuctionItem("mug", "Adrian Butiu Limited Mug", "₱500",   "4h 21m 5s",   R.drawable.sample_mug,      false),
            AuctionItem("tee", "Adrian Butiu Limited Tee", "₱1,000", "11h 17m 35s", R.drawable.sample_tee_wacky,      true),
            AuctionItem("cal", "Adrian Butiu Calendar",    "₱300",   "2h 14m 5s",   R.drawable.sample_calendar, false)
        )
    }

    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = BgGray,
        bottomBar = {
            BottomNavBar(
                selectedTab   = selectedTab,
                onTabSelected = { idx ->
                    selectedTab = idx
                    when (idx) {
                        0 -> { /* already home */ }
                        1 -> navController.navigate("favourites")
                        2 -> navController.navigate("auctions")
                        3 -> navController.navigate("account")
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(innerPadding)
                .verticalScroll(scrollState)
        ) {

            // ── Top bar ───────────────────────────────────────────────────────
            Row(
                modifier          = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ivy_pfp),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .clickable { navController.navigate("account") }
                )

                Spacer(Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text       = "Welcome",
                        fontFamily = InterFont,
                        fontSize   = 12.sp,
                        color      = Color(0xFF888880)
                    )
                    Text(
                        text       = "Ivy Timoteo",
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 18.sp,
                        color      = TextDark
                    )
                }

                IconButton(
                    onClick  = { navController.navigate("notifications") },
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color(0xFFD0CFC8), CircleShape)
                ) {
                    Icon(
                        imageVector        = Icons.Outlined.Notifications,
                        contentDescription = "Notifications",
                        tint               = TextDark
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
                Icon(
                    imageVector        = Icons.Outlined.MoreVert,
                    contentDescription = "Filter",
                    tint               = Color.Gray,
                    modifier           = Modifier.size(20.dp)
                )
            }

            Spacer(Modifier.height(20.dp))

            // ── Payamanan title ───────────────────────────────────────────────
            Text(
                text       = "Payamanan",
                fontFamily = InriaSerif,
                fontWeight = FontWeight.Bold,
                fontSize   = 26.sp,
                color      = OliveGreen,
                modifier   = Modifier.padding(horizontal = 20.dp)
            )

            Spacer(Modifier.height(12.dp))

            // ── Popular Auctions card ─────────────────────────────────────────
            Card(
                modifier  = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 12.dp),
                shape     = RoundedCornerShape(20.dp),
                colors    = CardDefaults.cardColors(containerColor = OliveGreen),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(16.dp)
                ) {

                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment     = Alignment.CenterVertically
                    ) {
                        Text(
                            text       = "Popular Auctions",
                            fontFamily = InterFont,
                            fontWeight = FontWeight.Bold,
                            fontSize   = 18.sp,
                            color      = Color.White
                        )
                        TextButton(onClick = { navController.navigate("auctions") }) {
                            Text(
                                text       = "View All",
                                fontFamily = InterFont,
                                fontSize   = 13.sp,
                                color      = Color(0xFFD4C9A0)
                            )
                        }
                    }

                    Text(
                        text     = "• • •",
                        color    = Color(0xFFD4C9A0),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val rows = sampleItems.chunked(2)
                    rows.forEach { rowItems ->
                        Row(
                            modifier              = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            rowItems.forEach { item ->
                                Box(modifier = Modifier.weight(1f).wrapContentHeight()) {
                                    if (item.id == "add") {
                                        AddItemCard(onClick = { navController.navigate("add-item") })
                                    } else {
                                        AuctionCard(
                                            item       = item,
                                            onFavClick = {
                                                val idx = sampleItems.indexOfFirst { it.id == item.id }
                                                if (idx != -1) sampleItems[idx] =
                                                    sampleItems[idx].copy(isFavourite = !sampleItems[idx].isFavourite)
                                            },
                                            onBidClick = { navController.navigate("product-page/${item.id}") }
                                        )
                                    }
                                }
                            }
                            if (rowItems.size == 1) Spacer(Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(10.dp))
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ── Add-item placeholder ──────────────────────────────────────────────────────
@Composable
private fun AddItemCard(onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .clickable { onClick() },
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = Color(0xFFF0EFE9)),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(
            modifier            = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier         = Modifier
                    .size(40.dp)
                    .border(2.dp, Color(0xFFAAAAAA), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Outlined.Add,
                    contentDescription = "Add",
                    tint               = Color(0xFFAAAAAA),
                    modifier           = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text       = "Add Item to Auction",
                fontFamily = InterFont,
                fontSize   = 12.sp,
                color      = Color(0xFF888880),
                textAlign  = TextAlign.Center,
                modifier   = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

// ── Auction item card ──────────────────────────────────────────────────────────
@Composable
private fun AuctionCard(
    item       : AuctionItem,
    onFavClick : () -> Unit,
    onBidClick : () -> Unit
) {
    Card(
        modifier  = Modifier.fillMaxWidth(),
        shape     = RoundedCornerShape(14.dp),
        colors    = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column {
            Box(
                modifier          = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .background(Color(0xFFF0EFE9)),
                contentAlignment  = Alignment.Center
            ) {
                if (item.imageRes != null) {
                    Image(
                        painter            = painterResource(id = item.imageRes),
                        contentDescription = item.title,
                        contentScale       = ContentScale.Fit,
                        modifier           = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 130.dp)
                            .padding(8.dp)
                    )
                }

                IconButton(
                    onClick  = onFavClick,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(28.dp)
                ) {
                    Icon(
                        imageVector        = if (item.isFavourite) Icons.Filled.Favorite
                        else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favourite",
                        tint               = if (item.isFavourite) HeartRed else Color.White,
                        modifier           = Modifier.size(18.dp)
                    )
                }
            }

            Column(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)) {
                Text(
                    text       = item.title,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    fontSize   = 11.sp,
                    color      = TextDark,
                    maxLines   = 2,
                    overflow   = TextOverflow.Ellipsis
                )
                Text(
                    text       = item.timeLeft,
                    fontFamily = InterFont,
                    fontSize   = 10.sp,
                    color      = Color(0xFF888880)
                )
                Spacer(Modifier.height(4.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    Text(
                        text       = item.price,
                        fontFamily = InterFont,
                        fontWeight = FontWeight.Bold,
                        fontSize   = 13.sp,
                        color      = TextDark
                    )
                    Button(
                        onClick        = onBidClick,
                        shape          = RoundedCornerShape(8.dp),
                        colors         = ButtonDefaults.buttonColors(containerColor = GoldBtn),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier       = Modifier.height(28.dp)
                    ) {
                        Text(
                            text       = "Place a Bid",
                            fontFamily = InterFont,
                            fontSize   = 10.sp,
                            color      = Color.White
                        )
                    }
                }
            }
        }
    }
}

// ── Bottom navigation bar ─────────────────────────────────────────────────────
@Composable
private fun BottomNavBar(
    selectedTab   : Int,
    onTabSelected : (Int) -> Unit
) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
    ) {
        val items = listOf(
            Icons.Outlined.Home           to "Home",
            Icons.Outlined.FavoriteBorder to "Favourites",
            Icons.Outlined.ShoppingCart   to "Auctions",
            Icons.Outlined.Settings       to "Settings"
        )
        items.forEachIndexed { index, (icon, label) ->
            NavigationBarItem(
                selected = selectedTab == index,
                onClick  = { onTabSelected(index) },
                icon     = {
                    Icon(
                        imageVector        = icon,
                        contentDescription = label,
                        modifier           = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor   = OliveGreen,
                    unselectedIconColor = Color(0xFF888880),
                    indicatorColor      = Color.Transparent
                )
            )
        }
    }
}