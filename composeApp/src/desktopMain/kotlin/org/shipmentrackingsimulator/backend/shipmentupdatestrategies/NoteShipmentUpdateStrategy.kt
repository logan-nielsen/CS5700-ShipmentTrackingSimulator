package org.shipmentrackingsimulator.backend.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.TrackingSimulator
import java.util.Date

class NoteShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipment = TrackingSimulator.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addNote(otherInfo)
    }
}