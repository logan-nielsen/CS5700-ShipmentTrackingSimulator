package org.shipmentrackingsimulator.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.ShipmentObserver
import org.shipmentrackingsimulator.shipmenttrackers.ShipmentTracker

class TrackerViewHelper : ShipmentObserver {
    val shipmentID = mutableStateOf<String?>(null)

    val shipmentStatus = mutableStateOf<String?>(null)

    val currentLocation = mutableStateOf<String?>(null)

    val expectedShipmentDeliveryDate = mutableStateOf<String?>(null)

    val shipmentNotes = mutableStateListOf<String>()

    val shipmentUpdateHistory = mutableStateListOf<String>()

    fun trackShipmentID(id: String): Boolean {
        val shipment = ShipmentTracker.findShipment(id)
        if (shipment != null) {
            shipment.registerObserver(this)
            shipmentID.value = id
            return true
        } else {
            return false
        }
    }

    fun stopTracking() {
        val shipment = ShipmentTracker.findShipment(shipmentID.value)
        shipment?.removeObserver(this)

        shipmentID.value = null
        shipmentStatus.value = null
        currentLocation.value = null
        expectedShipmentDeliveryDate.value = null
        shipmentNotes.clear()
        shipmentUpdateHistory.clear()
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