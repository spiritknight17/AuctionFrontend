package com.payamanan.auctionfrontend.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.payamanan.auctionfrontend.data.model.Auction
import com.payamanan.auctionfrontend.ui.theme.AlertRed

@Composable
fun EndAuctionDialog(auction: Auction, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("End Auction?", fontWeight = FontWeight.Bold) },
        text = { Text("Are you sure you want to end the auction? You will receive ${auction.item.name}. This action cannot be undone.") },
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
