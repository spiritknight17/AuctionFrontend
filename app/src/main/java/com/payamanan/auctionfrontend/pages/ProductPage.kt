package com.payamanan.auctionfrontend.pages

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import com.payamanan.auctionfrontend.data.UserSesssion
import com.payamanan.auctionfrontend.data.model.BidRequest
import com.payamanan.auctionfrontend.ui.theme.DarkText
import com.payamanan.auctionfrontend.ui.theme.GoldYellow
import com.payamanan.auctionfrontend.ui.theme.LightGray
import com.payamanan.auctionfrontend.ui.theme.OffWhite
import com.payamanan.auctionfrontend.ui.theme.OliveGreen
import com.payamanan.auctionfrontend.ui.theme.SubtleGray
import com.payamanan.auctionfrontend.viewModels.AuctionViewModel
import kotlinx.coroutines.delay


@Composable
fun ProductPage(navController: NavController, auctionId: String? = null) {
    val auctionViewModel: AuctionViewModel = viewModel()
    val auctions by auctionViewModel.auctions.collectAsState()
    val auction = auctions.find { it.id.toString() == auctionId }
    val user = UserSesssion.user

    if (auction == null) {
        // Handle case where auction is not found or loading
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = GoldYellow)
        }
        return
    }

    var bidAmount by remember { mutableFloatStateOf(auction.currentBid?.offeredPrice ?: auction.startingPrice) }
    val stepAmount = 100f

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(OliveGreen)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp)
                .padding(horizontal = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    
                    val endTime = auction.endTime.time
                    var timeLeft by remember { mutableLongStateOf((endTime - System.currentTimeMillis()) / 1000) }

                    LaunchedEffect(Unit) {
                        while (timeLeft > 0) {
                            delay(1000)
                            timeLeft = (endTime - System.currentTimeMillis()) / 1000
                        }
                    }

                    if (timeLeft > 0) {
                        Text(
                            text = "%02d:%02d:%02d".format(timeLeft / 3600, (timeLeft % 3600) / 60, timeLeft % 60),
                            color = GoldYellow,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    } else {
                        Text(
                            text = "Auction Ended",
                            color = GoldYellow,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
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

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = auction.item.name,
                color = Color.White,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = auction.item.description,
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
                        text = "₱${"%,.2f".format(auction.currentBid?.offeredPrice ?: auction.startingPrice)}",
                        color = GoldYellow,
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    // Note: BidRequest currently only has userId, fetching username would require additional logic/API call.
                    // For now, displaying "User ID: {id}" or similar if username not available in BidRequest
                    Text(
                        text = "User ID: ${auction.currentBid?.userId ?: "None"}", 
                        color = Color.White,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

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

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Place a Bid",
                color = DarkText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Initial Price: ₱${"%,.2f".format(auction.startingPrice)}",
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
                        onClick = { if (bidAmount > (auction.currentBid?.offeredPrice ?: auction.startingPrice)) bidAmount -= stepAmount },
                        modifier = Modifier.size(46.dp)
                    ) {
                        Text("-", fontSize = 26.sp, color = DarkText, fontWeight = FontWeight.Light)
                    }
                }

                Spacer(modifier = Modifier.width(24.dp))

                Text(
                    text = "₱${"%,.2f".format(bidAmount)}",
                    color = DarkText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.width(24.dp))

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
                onClick = {
                    user?.userId?.let { uid ->
                         val bidRequest = BidRequest(id = null, userId = uid, offeredPrice = bidAmount)
                         auction.id?.let { aid ->
                             auctionViewModel.bid(aid, bidRequest)
                             navController.popBackStack()
                         }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldYellow)
            ) {
                Text(
                    text = "₱${"%,.2f".format(bidAmount)}",
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
