package com.payamanan.auctionfrontend.sharedComponents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.payamanan.auctionfrontend.pages.AuctionItem
import com.payamanan.auctionfrontend.pages.InterFont
import com.payamanan.auctionfrontend.ui.theme.TextDark
import com.payamanan.auctionfrontend.ui.theme.AlertRed
import com.payamanan.auctionfrontend.ui.theme.GoldBtn

@Composable
fun HostedAuctionCard(
    item: AuctionItem,
    onEditClick: () -> Unit,
    onEndAuctionClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .clickable { onEditClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1.1f)) {
                Text(
                    text = item.title,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = TextDark,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 16.sp
                )
                Text(
                    text = item.price,
                    fontFamily = InterFont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFF4E5B2E)
                )
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
        }
    }
}