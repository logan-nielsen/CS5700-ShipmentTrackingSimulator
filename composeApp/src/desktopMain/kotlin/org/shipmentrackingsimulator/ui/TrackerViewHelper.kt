package org.shipmentrackingsimulator.ui

import androidx.compose.runtime.mutableStateOf
import org.shipmentrackingsimulator.backend.Shipment
import backend.ShipmentObserver
import org.shipmentrackingsimulator.backend.TrackingSimulator
import kotlin.collections.mutableListOf

class TrackerViewHelper : ShipmentObserver {
    val shipmentID = mutableStateOf<String?>(null)

    val shipmentStatus = mutableStateOf<String?>(null)

    val currentLocation = mutableStateOf<String?>(null)

    val expectedShipmentDeliveryDate = mutableStateOf<String?>(null)

    val shipmentNotes = mutableListOf<String>()

    val shipmentUpdateHistory = mutableListOf<String>()

    fun trackShipmentID(id: String) {
        val shipment = TrackingSimulator.findShipment(id)
        shipment?.registerObserver(this)

        shipmentID.value = id
    }

    fun stopTracking() {
        val shipment = TrackingSimulator.findShipment(shipmentID.value)
        shipment?.registerObserver(this)

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
            it.toString()
        })
    }
}