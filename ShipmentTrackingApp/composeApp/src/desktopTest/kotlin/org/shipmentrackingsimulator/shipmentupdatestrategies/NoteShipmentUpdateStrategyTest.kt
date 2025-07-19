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

class NoteShipmentUpdateStrategyTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        ShipmentTracker.reset()
    }

    @Test
    fun testNoteShipmentStrategy() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = NoteShipmentUpdateStrategy()
        strategy.update("TEST123", "note", Date(), "test note")

        val shipment = ShipmentTracker.findShipment("TEST123")
        assertNotNull(shipment)
        assertEquals("created", shipment.status)
        assertNull(shipment.currentLocation)
        assertNull(shipment.expectedDeliveryDate)
        assertEquals(1, shipment.getNotes().size)
        assertEquals("test note", shipment.getNotes()[0])
        assertEquals(0, shipment.getUpdateHistory().size)
    }

    @Test
    fun testNoteShipmentStrategyInvalidShipmentID() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = NoteShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "note", Date(1652712855468), "test note")
        }
    }

    @Test
    fun testNoteShipmentStrategyMissingOtherInfo() {
        val shipmentFactory = ShipmentFactory()
        ShipmentTracker.addShipment(shipmentFactory.create("TEST123", "created", "standard"))

        val strategy = NoteShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "note", Date(1652712855468), null)
        }
    }
}