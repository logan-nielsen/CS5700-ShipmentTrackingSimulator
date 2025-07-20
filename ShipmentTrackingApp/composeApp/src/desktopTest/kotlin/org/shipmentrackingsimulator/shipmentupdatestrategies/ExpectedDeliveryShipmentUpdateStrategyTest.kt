package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipments.ShipmentFactory
import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import java.text.ParseException
import java.text.SimpleDateFormat
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
        WebShipmentTracker.reset()
    }

    @Test
    fun testExpectedDeliveryShipmentStrategy() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
        val deliveryDate = dateFormatter.parse("2025-01-01")

        var strategy = ExpectedDeliveryShipmentUpdateStrategy()
        strategy.update("TEST123", "shipped", Date(), "2025-01-01")

        var shipment = WebShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("shipped", shipment.status)
        assertEquals(deliveryDate, shipment.expectedDeliveryDate)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)

        val delayedDate = dateFormatter.parse("2025-02-01")

        strategy = ExpectedDeliveryShipmentUpdateStrategy()
        strategy.update("TEST123", "delayed", Date(), "2025-02-01")

        shipment = WebShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("delayed", shipment.status)
        assertEquals(delayedDate, shipment.expectedDeliveryDate)
        assertNull(shipment.currentLocation)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(2, shipment.getUpdateHistory().size)
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyInvalidDate() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<ParseException> {
            strategy.update("TEST123", "shipped", Date(), "INVALID")
        }
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyInvalidShipmentID() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "shipped", Date(), "2025-01-01")
        }
    }

    @Test
    fun testExpectedDeliveryShipmentStrategyMissingOtherInfo() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = ExpectedDeliveryShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "shipped", Date(), null)
        }
    }
}