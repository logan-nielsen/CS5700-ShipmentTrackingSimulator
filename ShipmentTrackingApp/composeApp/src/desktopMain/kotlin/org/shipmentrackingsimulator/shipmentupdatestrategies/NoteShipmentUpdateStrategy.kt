package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.ShipmentTracker
import java.util.Date

class NoteShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipment = ShipmentTracker.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addNote(otherInfo)
    }
}