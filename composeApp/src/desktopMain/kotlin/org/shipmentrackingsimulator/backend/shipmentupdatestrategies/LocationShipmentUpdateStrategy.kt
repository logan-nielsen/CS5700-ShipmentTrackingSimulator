package org.shipmentrackingsimulator.backend.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.TrackingSimulator
import java.util.Date

class LocationShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        val shipment = TrackingSimulator.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.currentLocation = otherInfo
    }
}