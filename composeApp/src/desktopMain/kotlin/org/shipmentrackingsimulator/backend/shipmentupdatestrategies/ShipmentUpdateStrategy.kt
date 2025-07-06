package org.shipmentrackingsimulator.backend.shipmentupdatestrategies

import java.util.Date

interface ShipmentUpdateStrategy {
    fun update(
        shipmentId: String,
        status: String,
        dateOfUpdate: Date,
        otherInfo: String?
    )
}