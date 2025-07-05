package backend.shipmentupdatestrategies

import backend.TrackingSimulator
import java.util.Date

class OnlyStatusShipmentUpdateStrategy : ShipmentUpdateStrategy  {
    override fun update(
        shipmentId: String,
        status: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        val shipment = TrackingSimulator.findShipment(shipmentId)
        if (shipment == null) {
            throw IllegalArgumentException("Shipment with id $shipmentId does not exist")
        }

        shipment.addUpdate(status, dateOfUpdate)
    }
}