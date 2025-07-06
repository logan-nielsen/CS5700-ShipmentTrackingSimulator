package org.shipmentrackingsimulator.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.shipmentrackingsimulator.backend.TrackingSimulator
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var shipmentIDInput by remember { mutableStateOf("") }
        val trackerViewHelpers = remember { mutableStateListOf<TrackerViewHelper>() }

        LaunchedEffect(Unit) {
            launch {
                TrackingSimulator.runSimulation()
            }
        }

        fun addTrackerViewHelper(shipmentID: String) {
            if (shipmentID.isEmpty()) return

            val trackerViewHelper = TrackerViewHelper()
            trackerViewHelper.trackShipmentID(shipmentID)
            trackerViewHelpers += trackerViewHelper
            shipmentIDInput = ""
        }

        Column {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                TextField(
                    value = shipmentIDInput,
                    onValueChange = { shipmentIDInput = it },
                    placeholder = { Text("Enter Shipment ID") },
                )
                Button(
                    modifier = Modifier
                        .padding(end = 16.dp),
                    onClick = {
                        addTrackerViewHelper(shipmentIDInput)
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
                        TrackerView(trackerViewHelper)
                    }
                }
            }
        }
    }
}