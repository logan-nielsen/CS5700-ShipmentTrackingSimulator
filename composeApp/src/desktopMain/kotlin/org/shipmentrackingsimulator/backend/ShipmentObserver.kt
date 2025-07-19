package org.shipmentrackingsimulator.backend

import org.shipmentrackingsimulator.backend.shipments.Shipment

interface ShipmentObserver {
    fun update(shipment: Shipment)
}