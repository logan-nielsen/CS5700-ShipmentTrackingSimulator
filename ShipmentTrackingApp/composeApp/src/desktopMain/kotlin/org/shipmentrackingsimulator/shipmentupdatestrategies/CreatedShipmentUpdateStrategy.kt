package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.ShipmentTracker
import org.shipmentrackingsimulator.shipments.ShipmentFactory
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
        ShipmentTracker.addShipment(shipmentFactory.create(shipmentId, updateType, otherInfo))
    }
}