package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.ShipmentTracker
import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.shipments.ShipmentFactory
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ExpectedDeliveryShipmentUpdateStrategyTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        ShipmentTracker.reset()
    }

    @Test
    fun testExpectedDeliveryShipmentStrategy() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        var strategy = ExpectedDeliveryShipmentUpdateStrategy()
        strategy.update("TEST123", "shipped", Date(1652712855468), "1652713940874")

        var shipment = ShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("shipped", shipment.status)
        assertEquals(Date(1652713940874), shipment.expectedDeliveryDate)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)

        strategy = ExpectedDeliveryShipmentUpdateStrategy()
        strategy.update("TEST123", "delayed", Date(1652712855468), "1652718051403")

        shipment = ShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("delayed", shipment.status)
        assertEquals(Date(1652718051403), shipment.expectedDeliveryDate)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(2, shipment.getUpdateHistory().size)
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyInvalidDate() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<NumberFormatException> {
            strategy.update("TEST123", "shipped", Date(1652712855468), "INVALID")
        }
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyInvalidShipmentID() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "shipped", Date(1652712855468), "1652713940874")
        }
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyMissingOtherInfo() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "shipped", Date(1652712855468), null)
        }
    }
}