package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.ShipmentTracker
import java.util.Date

class ExpectedDeliveryShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipment = ShipmentTracker.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addUpdate(updateType, dateOfUpdate)
        shipment.expectedDeliveryDate = Date(otherInfo.toLong())
    }
}