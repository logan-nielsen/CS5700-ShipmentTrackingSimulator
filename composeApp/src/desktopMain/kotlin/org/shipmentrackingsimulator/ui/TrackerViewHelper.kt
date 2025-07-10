package org.shipmentrackingsimulator.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import org.shipmentrackingsimulator.backend.ShipmentObserver
import org.shipmentrackingsimulator.backend.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator

class TrackerViewHelper : ShipmentObserver {
    val shipmentID = mutableStateOf<String?>(null)

    val shipmentStatus = mutableStateOf<String?>(null)

    val currentLocation = mutableStateOf<String?>(null)

    val expectedShipmentDeliveryDate = mutableStateOf<String?>(null)

    val shipmentNotes = mutableStateListOf<String>()

    val shipmentUpdateHistory = mutableStateListOf<String>()

    fun trackShipmentID(id: String) {
        val shipment = TrackingSimulator.findShipment(id)
        shipment?.registerObserver(this)

        shipmentID.value = id
    }

    fun stopTracking() {
        val shipment = TrackingSimulator.findShipment(shipmentID.value)
        shipment?.removeObserver(this)

        shipmentID.value = null
    }

    override fun update(shipment: Shipment) {
        shipmentStatus.value = shipment.status
        currentLocation.value = shipment.currentLocation ?: "Unknown"
        expectedShipmentDeliveryDate.value = shipment.expectedDeliveryDate?.toString() ?: "--"
        shipmentNotes.clear()
        shipmentNotes.addAll(shipment.getNotes())
        shipmentUpdateHistory.clear()
        shipmentUpdateHistory.addAll(shipment.getUpdateHistory().map {
            "Shipment went from ${it.previousStatus} to ${it.newStatus} at ${it.date}"
        })
    }
}