package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.backend.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import org.shipmentrackingsimulator.backend.shipmentupdatestrategies.NoteShipmentUpdateStrategy
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
        TrackingSimulator.reset()
    }

    @Test
    fun testNoteShipmentStrategy() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = NoteShipmentUpdateStrategy()
        strategy.update("TEST123", "note", Date(), "test note")

        val shipment = TrackingSimulator.findShipment("TEST123")
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
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = NoteShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "note", Date(1652712855468), "test note")
        }
    }

    @Test
    fun testNoteShipmentStrategyMissingOtherInfo() {
        TrackingSimulator.addShipment(Shipment("TEST123", "created"))

        val strategy = NoteShipmentUpdateStrategy()
        assertFailsWith<IllegalArgumentException> {
            strategy.update("INVALID", "note", Date(1652712855468), null)
        }
    }
}