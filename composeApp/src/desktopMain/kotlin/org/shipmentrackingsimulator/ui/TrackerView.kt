package org.shipmentrackingsimulator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TrackerView(helper: TrackerViewHelper) {
    Column {
        Row {
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
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
            }
        }
    }
}