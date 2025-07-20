package org.shipmentrackingsimulator.shipmentupdatestrategies

class ShipmentUpdateStrategyFactory {
    private val shipmentUpdateStrategyMap = mapOf(
        "created" to CreatedShipmentUpdateStrategy(),
        "shipped" to ExpectedDeliveryShipmentUpdateStrategy(),
        "location" to LocationShipmentUpdateStrategy(),
        "delivered" to StatusShipmentUpdateStrategy(),
        "delayed" to ExpectedDeliveryShipmentUpdateStrategy(),
        "lost" to StatusShipmentUpdateStrategy(),
        "canceled" to StatusShipmentUpdateStrategy(),
        "noteadded" to NoteShipmentUpdateStrategy(),
    )

    fun create(updateType: String): ShipmentUpdateStrategy {
        return shipmentUpdateStrategyMap[updateType]
            ?: throw IllegalArgumentException("Unknown update type $updateType")
    }
}