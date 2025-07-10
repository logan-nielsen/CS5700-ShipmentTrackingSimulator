package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.StatusShipmentUpdateStrategy
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
        TrackingSimulator.reset()
    }

    @Test
    fun testStatusShipmentStrategy() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = StatusShipmentUpdateStrategy()
        strategy.update("TEST123", "status", Date(), "shipped")

        val shipment = TrackingSimulator.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("status", shipment.status)
        assertNull(shipment.currentLocation)
        assertNull(shipment.expectedDeliveryDate)
        assertEquals(0, shipment.getNotes().size)
        assertEquals(1, shipment.getUpdateHistory().size)
    }

    @Test
    fun testStatusShipmentStrategyInvalidShipmentID() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = StatusShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "status", Date(1652712855468), "shipped")
        }
    }

}