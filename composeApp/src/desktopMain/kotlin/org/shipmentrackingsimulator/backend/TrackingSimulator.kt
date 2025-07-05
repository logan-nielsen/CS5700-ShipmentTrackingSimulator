package backend

import backend.shipmentupdatestrategies.CreatedShipmentUpdateStrategy
import backend.shipmentupdatestrategies.ExpectedDeliveryShipmentUpdateStrategy
import backend.shipmentupdatestrategies.LocationShipmentUpdateStrategy
import backend.shipmentupdatestrategies.NoteShipmentUpdateStrategy
import backend.shipmentupdatestrategies.StatusShipmentUpdateStrategy
import backend.shipmentupdatestrategies.ShipmentUpdateStrategy
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

object TrackingSimulator {
    private val shipments = mutableListOf<Shipment>()
    private val shipmentUpdateStrategyMap = mapOf<String, ShipmentUpdateStrategy>(
        "created" to CreatedShipmentUpdateStrategy(),
        "shipped" to ExpectedDeliveryShipmentUpdateStrategy(),
        "location" to LocationShipmentUpdateStrategy(),
        "delivered" to StatusShipmentUpdateStrategy(),
        "delayed" to ExpectedDeliveryShipmentUpdateStrategy(),
        "lost" to StatusShipmentUpdateStrategy(),
        "cancelled" to StatusShipmentUpdateStrategy(),
        "noteadded" to NoteShipmentUpdateStrategy(),
    )

    fun findShipment(shipmentId: String?): Shipment? {
        if (shipmentId == null) return null

        return shipments.find { it.id == shipmentId }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    suspend fun runSimulation() = coroutineScope {
        launch {
            val lines = File("path/to/file.txt").readLines()
            lines.forEach { line ->
                val items = line.split(",")
                val (updateType, shipmentId, timestampOfUpdate, otherInfo) = items
                val shipmentUpdateStrategy = shipmentUpdateStrategyMap[updateType]
                    ?: throw IllegalArgumentException("Invalid update type: $updateType")

                shipmentUpdateStrategy.update(
                    shipmentId,
                    updateType,
                    Date(timestampOfUpdate.toLong()),
                    otherInfo
                )

                delay(1000L)
            }
        }
    }
}