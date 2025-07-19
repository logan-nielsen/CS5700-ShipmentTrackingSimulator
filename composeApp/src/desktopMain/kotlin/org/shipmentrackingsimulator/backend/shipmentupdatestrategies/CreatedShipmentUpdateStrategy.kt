package org.shipmentrackingsimulator.backend.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.TrackingSimulator
import org.shipmentrackingsimulator.backend.shipments.ShipmentFactory
import java.util.Date

class CreatedShipmentUpdateStrategy : ShipmentUpdateStrategy {
    override fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    ) {
        requireNotNull(otherInfo) { "otherInfo must not be null" }

        val shipmentFactory = ShipmentFactory()
        TrackingSimulator.addShipment(shipmentFactory.create(shipmentId, updateType, otherInfo))
    }
}