package org.shipmentrackingsimulator.backend.shipments

class ShipmentFactory {
    private val shipmentTypeMap = mapOf(
        "Standard" to ::StandardShipment,
        "Express" to ::ExpressShipment,
        "Bulk" to ::BulkShipment,
        "Overnight" to ::OvernightShipment,
    )

    fun create(id: String, status: String, tpe: String): Shipment {
        return shipmentTypeMap[tpe]?.invoke(id, status)
            ?: throw Exception("Unknown shipment type $tpe")
    }
}