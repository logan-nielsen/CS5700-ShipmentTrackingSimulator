package backend.shipmentupdatestrategies

import backend.Shipment
import backend.TrackingSimulator
import java.util.Date

class CreatedShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        status: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        TrackingSimulator.addShipment(Shipment(shipmentId, status))
    }
}