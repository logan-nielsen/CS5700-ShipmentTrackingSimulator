package org.shipmentrackingsimulator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TrackerView(helper: TrackerViewHelper, remove: () -> Unit) {
    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                Column {
                    Text("Shipment ID: ${helper.shipmentID.value}")
                    Text("Status: ${helper.shipmentStatus.value}")
                    Text("Current Location: ${helper.currentLocation.value}")
                    Text("Expected Delivery Date: ${helper.expectedShipmentDeliveryDate.value}")

                    Text("Notes:")
                    for (note in helper.shipmentNotes) {
                        Text(note)
                    }

                    Text("Update History:")
                    for (update in helper.shipmentUpdateHistory) {
                        Text(update)
                    }
                }

                IconButton(onClick = remove) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                    )
                }
            }
        }

    }
}