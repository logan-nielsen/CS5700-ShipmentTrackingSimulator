package org.shipmentrackingsimulator

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import org.shipmentrackingsimulator.backend.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import org.shipmentrackingsimulator.ui.TrackerViewHelper
import java.util.Date
import kotlin.test.BeforeTest

class TrackerViewHelperTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        TrackingSimulator.reset()
    }

    @Test
    fun testTrackShipment() {
        val shipment = Shipment("TEST123", "created")
        TrackingSimulator.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")

        assertEquals("TEST123", helper.shipmentID.value)
        assertEquals("created", helper.shipmentStatus.value)
        assertEquals("Unknown", helper.currentLocation.value)
        assertEquals("--", helper.expectedShipmentDeliveryDate.value)
    }

    @Test
    fun testStopTracking() {
        val shipment = Shipment("TEST123", "created")
        TrackingSimulator.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")
        helper.stopTracking()

        assertNull(helper.shipmentID.value)
        assertNull(helper.shipmentStatus.value)
        assertNull(helper.currentLocation.value)
        assertNull(helper.expectedShipmentDeliveryDate.value)
        assertEquals(0, helper.shipmentNotes.size)
        assertEquals(0, helper.shipmentUpdateHistory.size)
    }

    @Test
    fun testShipmentUpdates() {
        val shipment = Shipment("TEST123", "created")
        TrackingSimulator.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")

        shipment.currentLocation = "New York"
        shipment.addNote("First note")
        shipment.addNote("Second note")
        shipment.addUpdate("shipped", Date())
        shipment.addUpdate("delivered", Date())

        assertEquals("New York", helper.currentLocation.value)
        assertEquals(2, helper.shipmentNotes.size)
        assertEquals("First note", helper.shipmentNotes[0])
        assertEquals("Second note", helper.shipmentNotes[1])
        assertEquals(2, helper.shipmentUpdateHistory.size)
        assertEquals(
            "Shipment went from created to shipped at ${shipment.getUpdateHistory()[0].date}",
            helper.shipmentUpdateHistory[0]
        )
        assertEquals(
            "Shipment went from shipped to delivered at ${shipment.getUpdateHistory()[1].date}",
            helper.shipmentUpdateHistory[1]
        )
    }
}