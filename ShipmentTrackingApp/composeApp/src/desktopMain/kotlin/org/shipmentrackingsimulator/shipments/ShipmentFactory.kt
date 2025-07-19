package org.shipmentrackingsimulator.shipments

class ShipmentFactory {
    private val shipmentTypeMap = mapOf(
        "standard" to ::StandardShipment,
        "express" to ::ExpressShipment,
        "bulk" to ::BulkShipment,
        "overnight" to ::OvernightShipment,
    )

    fun create(id: String, status: String, type: String): Shipment {
        return shipmentTypeMap[type]?.invoke(id, status)
            ?: throw Exception("Unknown shipment type $type")
    }
}