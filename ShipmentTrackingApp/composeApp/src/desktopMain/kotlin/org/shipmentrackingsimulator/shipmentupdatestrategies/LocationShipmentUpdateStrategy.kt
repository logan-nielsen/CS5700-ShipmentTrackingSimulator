package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import java.util.Date

class LocationShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        val shipment = WebShipmentTracker.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.currentLocation = otherInfo
    }
}