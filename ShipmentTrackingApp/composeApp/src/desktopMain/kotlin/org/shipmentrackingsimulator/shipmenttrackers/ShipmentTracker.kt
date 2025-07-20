package org.shipmentrackingsimulator.shipmenttrackers

import org.shipmentrackingsimulator.shipments.Shipment

abstract class ShipmentTracker {
    private val shipments = mutableListOf<Shipment>()

    fun findShipment(shipmentId: String?): Shipment? {
        if (shipmentId == null) return null

        return shipments.find { it.id == shipmentId }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun reset() {
        shipments.clear()
    }

    abstract fun run()
}