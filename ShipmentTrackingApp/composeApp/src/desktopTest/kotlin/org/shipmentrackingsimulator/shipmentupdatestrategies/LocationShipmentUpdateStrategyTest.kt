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

class LocationShipmentUpdateStrategyTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        WebShipmentTracker.reset()
    }

    @Test
    fun testLocationShipmentStrategy() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = LocationShipmentUpdateStrategy()
        strategy.update("TEST123", "location", Date(), "some location")

        val shipment = WebShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("created", shipment.status)
        assertEquals("some location", shipment.currentLocation)
        assertNull(shipment.expectedDeliveryDate)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(0, shipment.getUpdateHistory().size)
    }

    @Test
    fun testLocationShipmentStrategyInvalidShipmentID() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = LocationShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "location", Date(1652712855468), "1652713940874")
        }
    }

    @Test
    fun testLocationShipmentStrategyMissingOtherInfo() {
        val shipmentFactory = ShipmentFactory()
        WebShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = LocationShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "location", Date(1652712855468), null)
        }
    }
}