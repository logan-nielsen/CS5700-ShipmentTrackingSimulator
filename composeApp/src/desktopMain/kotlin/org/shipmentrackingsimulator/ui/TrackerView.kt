package org.shipmentrackingsimulator.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun TrackerView(shipmentID: String) {
    val helper = TrackerViewHelper()
    helper.trackShipmentID(shipmentID)

    Column {
        Row {
            Text("Tracking Shipment: ${helper.shipmentID.value}")
        }
    }
}