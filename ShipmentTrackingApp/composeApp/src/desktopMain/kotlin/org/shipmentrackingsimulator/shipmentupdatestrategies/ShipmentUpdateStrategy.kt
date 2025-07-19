package org.shipmentrackingsimulator.shipmentupdatestrategies

import java.util.Date

interface ShipmentUpdateStrategy {
    fun update(
        shipmentId: String,
        updateType: String,
        dateOfUpdate: Date,
        otherInfo: String?
    )
}