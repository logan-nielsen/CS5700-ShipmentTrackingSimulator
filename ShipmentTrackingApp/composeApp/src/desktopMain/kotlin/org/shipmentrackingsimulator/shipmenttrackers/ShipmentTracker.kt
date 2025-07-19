package org.shipmentrackingsimulator.shipmenttrackers

import org.shipmentrackingsimulator.shipments.Shipment

abstract class ShipmentTracker {
    abstract fun findShipment(shipmentId: String?): Shipment?

    abstract fun addShipment(shipment: Shipment)

    abstract fun reset()

    abstract suspend fun run(updateDelay: Long = 1000L)
}