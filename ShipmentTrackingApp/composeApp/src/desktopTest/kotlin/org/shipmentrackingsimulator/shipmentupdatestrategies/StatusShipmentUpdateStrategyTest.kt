package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipments.ShipmentFactory
import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StatusShipmentUpdateStrategyTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        WebShipmentTracker.reset()
    }

    @Test
    fun testStatusShipmentStrategy() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = StatusShipmentUpdateStrategy()
        strategy.update("TEST123", "status", Date(), "shipped")

        val shipment = WebShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("status", shipment.status)
        assertNull(shipment.currentLocation)
        assertNull(shipment.expectedDeliveryDate)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)
    }

    @Test
    fun testStatusShipmentStrategyInvalidShipmentID() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = StatusShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "status", Date(1652712855468), "shipped")
        }
    }

}