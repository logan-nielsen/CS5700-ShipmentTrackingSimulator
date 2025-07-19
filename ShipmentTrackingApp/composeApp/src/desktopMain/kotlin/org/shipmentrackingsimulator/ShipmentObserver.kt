package org.shipmentrackingsimulator

import org.shipmentrackingsimulator.shipments.Shipment

interface ShipmentObserver {
    fun update(shipment: Shipment)
}