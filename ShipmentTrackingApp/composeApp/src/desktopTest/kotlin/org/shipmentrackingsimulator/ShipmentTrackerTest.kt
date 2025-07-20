package org.shipmentrackingsimulator

import org.shipmentrackingsimulator.shipments.ShipmentFactory
import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ShipmentTrackerTest {
    @BeforeTest
    fun resetShipmentTracker() {
        WebShipmentTracker.reset()
    }

    @Test
    fun testFindShipment() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "standard")
        WebShipmentTracker.addShipment(shipment)

        val found = WebShipmentTracker.findShipment("TEST123")
        assertNotNull(found)
        assertEquals("TEST123", found.id)
        assertEquals("created", found.status)
    }

    @Test
    fun testFindMultipleShipments() {
        val shipmentFactory = ShipmentFactory()
        val shipment1 = shipmentFactory.create("TEST1", "created", "standard")
        val shipment2 = shipmentFactory.create("TEST2", "processing", "standard")
        val shipment3 = shipmentFactory.create("TEST3", "shipped", "standard")

        WebShipmentTracker.addShipment(shipment1)
        WebShipmentTracker.addShipment(shipment2)
        WebShipmentTracker.addShipment(shipment3)

        assertEquals("created", WebShipmentTracker.findShipment("TEST1")?.status)
        assertEquals("processing", WebShipmentTracker.findShipment("TEST2")?.status)
        assertEquals("shipped", WebShipmentTracker.findShipment("TEST3")?.status)
    }

    @Test
    fun testFindNonExistentShipment() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "standard")
        WebShipmentTracker.addShipment(shipment)

        val notFound = WebShipmentTracker.findShipment("INVALID")
        assertNull(notFound)
    }

    @Test
    fun testReset() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "standard")
        WebShipmentTracker.addShipment(shipment)

        WebShipmentTracker.reset()

        val notFound = WebShipmentTracker.findShipment("TEST123")
        assertNull(notFound)
    }
}
  