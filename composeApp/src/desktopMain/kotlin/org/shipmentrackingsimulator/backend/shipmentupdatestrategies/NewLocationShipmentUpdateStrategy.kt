package backend.shipmentupdatestrategies

import backend.TrackingSimulator
import java.util.Date

class NewLocationShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        status: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        val shipment = TrackingSimulator.findShipment(shipmentId)
        requireNotNull(shipment) { "Shipment with id $shipmentId does not exist" }

        shipment.addUpdate(status, dateOfUpdate)
        shipment.currentLocation = otherInfo
    }
}