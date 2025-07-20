package org.shipmentrackingsimulator.shipments

import java.util.Date

class ShipmentFactory {
    private val shipmentTypeMap = mapOf(
        "standard" to ::StandardShipment,
        "express" to ::ExpressShipment,
        "bulk" to ::BulkShipment,
        "overnight" to ::OvernightShipment,
    )

    fun create(id: String, status: String, type: String, creationDate: Date = Date()): Shipment {
        return shipmentTypeMap[type]?.invoke(id, status, creationDate)
            ?: throw IllegalArgumentException("Unknown shipment type $type")
    }
}