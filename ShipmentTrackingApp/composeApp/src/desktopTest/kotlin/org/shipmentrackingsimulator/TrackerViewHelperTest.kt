package org.shipmentrackingsimulator

import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.shipmenttrackers.ShipmentTracker
import org.shipmentrackingsimulator.ui.TrackerViewHelper
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class TrackerViewHelperTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        ShipmentTracker.reset()
    }

    @Test
    fun testTrackShipment() {
        val shipment = Shipment("TEST123", "created")
        ShipmentTracker.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")

        assertEquals("TEST123", helper.shipmentID.value)
        assertEquals("created", helper.shipmentStatus.value)
        assertEquals("Unknown", helper.currentLocation.value)
        assertEquals("--", helper.expectedShipmentDeliveryDate.value)
    }

    @Test
    fun testTrackNonExistentShipment() {
        val helper = TrackerViewHelper()
        helper.trackShipmentID("NONEXISTENT")

        assertNull(helper.shipmentID.value)
        assertNull(helper.shipmentStatus.value)
        assertNull(helper.currentLocation.value)
        assertNull(helper.expectedShipmentDeliveryDate.value)
    }

    @Test
    fun testTrackShipmentWithExpectedDeliveryDate() {
        val shipment = Shipment("TEST123", "created")
        val expectedDate = Date()
        shipment.expectedDeliveryDate = expectedDate
        ShipmentTracker.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")

        assertEquals(expectedDate.toString(), helper.expectedShipmentDeliveryDate.value)
    }

    @Test
    fun testStopTracking() {
        val shipment = Shipment("TEST123", "created")
        ShipmentTracker.addShipment(shipment)

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
    fun testStopTrackingNonExistentShipment() {
        val helper = TrackerViewHelper()
        helper.stopTracking()

        assertNull(helper.shipmentID.value)
        assertNull(helper.shipmentStatus.value)
        assertNull(helper.currentLocation.value)
        assertNull(helper.expectedShipmentDeliveryDate.value)
    }

    @Test
    fun testShipmentUpdates() {
        val shipment = Shipment("TEST123", "created")
        ShipmentTracker.addShipment(shipment)

        val helper = TrackerViewHelper()
        helper.trackShipmentID("TEST123")

        shipment.currentLocation = "New York"
        shipment.addNote("First note")
        shipment.addNote("Second note")
        shipment.addUpdate("shipped", Date())
        shipment.addUpdate("delivered", Date())

        assertEquals("delivered", helper.shipmentStatus.value)
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