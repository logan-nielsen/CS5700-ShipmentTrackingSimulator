package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.ShipmentTracker
import java.util.Date

class StatusShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        val shipment = ShipmentTracker.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addUpdate(updateType, dateOfUpdate)
    }
}