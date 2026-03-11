package com.payamanan.auctionfrontend.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.ui.theme.GoldBtn
import com.payamanan.auctionfrontend.ui.theme.ModalGray
import com.payamanan.auctionfrontend.ui.theme.TextDark

@Composable
fun AuctionFormDialog(
    auction: Auction,
    onDismiss: () -> Unit,
    onConfirm: (Auction) -> Unit
) {
    var desc by remember { mutableStateOf(auction.item.description) }
    var price by remember { mutableStateOf(auction.startingPrice.toString()) }

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
                    Text(text = auction.item.name, fontWeight = FontWeight.Bold, fontSize = 20.sp, color = TextDark)
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(Modifier.height(24.dp))

                DialogTextField(
                    label = "Item Description",
                    value = desc,
                    onValueChange = { desc = it })
                Spacer(Modifier.height(16.dp))
                DialogTextField(
                    label = "Initial Price",
                    value = price,
                    onValueChange = { price = it })

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        val newPrice = price.toFloatOrNull() ?: auction.startingPrice
                        val updatedItem = auction.item.copy(description = desc)
                        val updatedAuction = auction.copy(item = updatedItem, startingPrice = newPrice)
                        onConfirm(updatedAuction)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .height(48.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldBtn)
                ) {
                    Text(text = "Save", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
