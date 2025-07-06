package backend

import org.shipmentrackingsimulator.backend.Shipment

interface ShipmentObserver {
    fun update(shipment: Shipment)
}