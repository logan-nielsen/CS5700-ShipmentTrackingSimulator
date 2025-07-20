package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import java.text.SimpleDateFormat
import java.util.Date

class ExpectedDeliveryShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipment = WebShipmentTracker.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addUpdate(updateType, dateOfUpdate)

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        shipment.expectedDeliveryDate = dateFormatter.parse(otherInfo)
    }
}