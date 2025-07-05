package backend

import backend.shipmentupdatestrategies.OnlyStatusShipmentUpdateStrategy
import backend.shipmentupdatestrategies.ShipmentUpdateStrategy
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

object TrackingSimulator {
    private val shipments = mutableListOf<Shipment>()
    private val shipmentUpdateStrategyMap = mapOf<String, ShipmentUpdateStrategy>(
        "created" to TODO(),
        "shipped" to TODO(),
        "location" to TODO(),
        "delivered" to OnlyStatusShipmentUpdateStrategy(),
        "delayed" to TODO(),
        "lost" to OnlyStatusShipmentUpdateStrategy(),
        "cancelled" to OnlyStatusShipmentUpdateStrategy(),
        "noteadded" to TODO(),
    )

    fun findShipment(shipmentId: String): Shipment? {
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