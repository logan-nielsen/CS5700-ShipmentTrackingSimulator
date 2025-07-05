package org.shipmentrackingsimulator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val shipmentID = rememberTextFieldState()
        val trackerViewHelpers = mutableListOf<TrackerViewHelper>()

        fun addTrackerViewHelper(shipmentID: String) {
            val trackerViewHelper = TrackerViewHelper()
            trackerViewHelper.trackShipmentID(shipmentID)
            trackerViewHelpers.add(trackerViewHelper)
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextField(
                state = shipmentID,
                placeholder = { Text("Enter Shipment ID") },
                modifier = Modifier
                    .weight(1f)
            )
            Button(
                modifier = Modifier
                    .padding(end = 16.dp),
                onClick = {
                    addTrackerViewHelper(shipmentID.toString())
                }
            ) {
                Text("Track")
            }
        }

        LazyColumn {
            items(trackerViewHelpers) { trackerViewHelper ->
                Column(
                    modifier = Modifier
                        .safeContentPadding()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text("Shipment ID: ${trackerViewHelper.shipmentID.value}")
                }
            }
        }
    }
}