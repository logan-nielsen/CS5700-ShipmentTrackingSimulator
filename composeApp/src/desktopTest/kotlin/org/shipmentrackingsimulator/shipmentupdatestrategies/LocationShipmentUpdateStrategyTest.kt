package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.LocationShipmentUpdateStrategy
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
        TrackingSimulator.reset()
    }

    @Test
    fun testLocationShipmentStrategy() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = LocationShipmentUpdateStrategy()
        strategy.update("TEST123", "location", Date(), "some location")

        val shipment = TrackingSimulator.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("created", shipment.status)
        assertEquals("some location", shipment.currentLocation)
        assertNull(shipment.expectedDeliveryDate)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(0, shipment.getUpdateHistory().size)
    }

    @Test
    fun testLocationShipmentStrategyInvalidShipmentID() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = LocationShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "location", Date(1652712855468), "1652713940874")
        }
    }

    @Test
    fun testLocationShipmentStrategyMissingOtherInfo() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = LocationShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "location", Date(1652712855468), null)
        }
    }
}