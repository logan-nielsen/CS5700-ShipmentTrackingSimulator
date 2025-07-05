package backend.shipmentupdatestrategies

import backend.TrackingSimulator
import java.util.Date

class NoteShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        status: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipment = TrackingSimulator.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addUpdate(status, dateOfUpdate)
        shipment.addNote(otherInfo)
    }
}