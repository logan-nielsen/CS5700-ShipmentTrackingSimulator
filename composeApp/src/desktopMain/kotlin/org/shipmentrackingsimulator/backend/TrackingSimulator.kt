package org.shipmentrackingsimulator.backend

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.shipmentrackingsimulator.backend.shipments.Shipment
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.CreatedShipmentUpdateStrategy
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.ExpectedDeliveryShipmentUpdateStrategy
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.LocationShipmentUpdateStrategy
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.NoteShipmentUpdateStrategy
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.ShipmentUpdateStrategy
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.StatusShipmentUpdateStrategy
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
        "canceled" to StatusShipmentUpdateStrategy(),
        "noteadded" to NoteShipmentUpdateStrategy(),
    )

    fun findShipment(shipmentId: String?): Shipment? {
        if (shipmentId == null) return null

        return shipments.find { it.id == shipmentId }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun reset() {
        shipments.clear()
    }

    suspend fun runSimulation(updateDelay: Long = 1000L) = coroutineScope {
        val lines = File("test.txt").readLines()
        lines.forEach { line ->
            val items = line.split(",")
            val (updateType, shipmentId, timestampOfUpdate) = items
            val otherInfo = items.getOrNull(3)

            val shipmentUpdateStrategy = shipmentUpdateStrategyMap[updateType]
                ?: throw IllegalArgumentException("Invalid update type: $updateType")

            shipmentUpdateStrategy.update(
                shipmentId,
                updateType,
                Date(timestampOfUpdate.toLong()),
                otherInfo
            )

            delay(updateDelay)
        }
    }
}