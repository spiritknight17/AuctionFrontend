package com.payamanan.auctionfrontend.sharedComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.viewModels.AuctionViewModel

@Composable
fun ItemToAuction(
    item: Item,
    viewModel: AuctionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onDismiss: () -> Unit
) {
    var startingPrice by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Text(
                    text = "Setup Auction for: ${item.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(text = item.description, fontSize = 14.sp, color = androidx.compose.ui.graphics.Color.Gray)

                Spacer(Modifier.height(24.dp))
                androidx.compose.material3.OutlinedTextField(
                    value = startingPrice,
                    onValueChange = { startingPrice = it },
                    label = { Text("Starting Price") },
                    modifier = Modifier.fillMaxWidth(),
                    prefix = { Text("₱ ") },
                    keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(
                        keyboardType = androidx.compose.ui.text.input.KeyboardType.Number
                    )
                )
                Spacer(Modifier.height(24.dp))
                Button(
                    onClick = {
                        val price = startingPrice.toFloatOrNull() ?: 0f
                        if (price > 0) {
                            viewModel.startAuctionProcess(item, price)
                            onDismiss()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = startingPrice.isNotEmpty()
                ) {
                    Text("Launch Auction")
                }

                androidx.compose.material3.TextButton(
                    onClick = onDismiss,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}