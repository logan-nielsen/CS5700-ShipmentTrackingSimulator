package org.shipmentrackingsimulator

import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.shipments.ShipmentFactory
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TrackingSimulatorTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        ShipmentTracker.reset()
    }

    @Test
    fun testFindShipment() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "standard")
        ShipmentTracker.addShipment(shipment)

        val found = ShipmentTracker.findShipment("TEST123")
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

        ShipmentTracker.addShipment(shipment1)
        ShipmentTracker.addShipment(shipment2)
        ShipmentTracker.addShipment(shipment3)

        assertEquals("created", ShipmentTracker.findShipment("TEST1")?.status)
        assertEquals("processing", ShipmentTracker.findShipment("TEST2")?.status)
        assertEquals("shipped", ShipmentTracker.findShipment("TEST3")?.status)
    }

    @Test
    fun testFindNonExistentShipment() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "standard")
        ShipmentTracker.addShipment(shipment)

        val notFound = ShipmentTracker.findShipment("INVALID")
        assertNull(notFound)
    }
}
  