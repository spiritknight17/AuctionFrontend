package com.payamanan.auctionfrontend.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.payamanan.auctionfrontend.data.model.Item
import com.payamanan.auctionfrontend.ui.theme.GoldBtn
import com.payamanan.auctionfrontend.ui.theme.ModalGray
import com.payamanan.auctionfrontend.ui.theme.TextDark
@Composable
fun AuctionFormDialog(
    item: Item,
    onDismiss: () -> Unit,
    onConfirm: (Item) -> Unit
) {
    var name by remember { mutableStateOf(item.name) }
    var desc by remember { mutableStateOf(item.description) }

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
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (item.id == null) "Add New Item" else "Edit Item",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = TextDark
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.Gray)
                    }
                }

                Spacer(Modifier.height(24.dp))

                // Input Fields
                DialogTextField(
                    label = "Item Name",
                    value = name,
                    onValueChange = { name = it }
                )
                Spacer(Modifier.height(16.dp))

                DialogTextField(
                    label = "Item Description",
                    value = desc,
                    onValueChange = { desc = it }
                )

                Spacer(Modifier.height(32.dp))

                Button(
                    onClick = {
                        val updatedItem = item.copy(
                            name = name,
                            description = desc
                        )
                        onConfirm(updatedItem)
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(50.dp),
                    shape = RoundedCornerShape(25.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = GoldBtn),
                    enabled = name.isNotBlank() && desc.isNotBlank()
                ) {
                    Text(
                        text = if (item.id == null) "Create Item" else "Save Changes",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}