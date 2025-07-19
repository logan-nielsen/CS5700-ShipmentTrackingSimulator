package org.shipmentrackingsimulator.shipmenttrackers

import kotlinx.coroutines.delay
import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.shipmentupdatestrategies.ShipmentUpdateStrategyFactory
import java.io.File
import java.util.Date

object ServerShipmentTracker: ShipmentTracker() {
    private val shipments = mutableListOf<Shipment>()

    override fun findShipment(shipmentId: String?): Shipment? {
        if (shipmentId == null) return null

        return shipments.find { it.id == shipmentId }
    }

    override fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    override fun reset() {
        shipments.clear()
    }

    override suspend fun run(updateDelay: Long) {
        val lines = File("test.txt").readLines()
        lines.forEach { line ->
            val items = line.split(",")
            val (updateType, shipmentId, timestampOfUpdate) = items
            val otherInfo = items.getOrNull(3)

            val shipmentUpdateStrategyFactory = ShipmentUpdateStrategyFactory()
            val shipmentUpdateStrategy = shipmentUpdateStrategyFactory.create(updateType)

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