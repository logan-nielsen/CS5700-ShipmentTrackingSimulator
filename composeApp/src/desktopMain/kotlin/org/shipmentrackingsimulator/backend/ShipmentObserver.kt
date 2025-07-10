package org.shipmentrackingsimulator.backend

interface ShipmentObserver {
    fun update(shipment: Shipment)
}