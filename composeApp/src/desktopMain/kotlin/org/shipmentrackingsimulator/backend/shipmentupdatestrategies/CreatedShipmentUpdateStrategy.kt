package org.shipmentrackingsimulator.backend.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.shipments.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import java.util.Date

class CreatedShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        TrackingSimulator.addShipment(Shipment(shipmentId, updateType))
    }
}