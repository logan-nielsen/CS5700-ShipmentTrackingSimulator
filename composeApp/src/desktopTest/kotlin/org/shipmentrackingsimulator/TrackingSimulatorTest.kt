package org.shipmentrackingsimulator

import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.shipmentrackingsimulator.backend.shipments.Shipment
import org.shipmentrackingsimulator.backend.TrackingSimulator
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class TrackingSimulatorTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        TrackingSimulator.reset()
    }

    @Test
    fun testFindShipment() {
        val shipment = Shipment("TEST123", "created")
        TrackingSimulator.addShipment(shipment)

        val found = TrackingSimulator.findShipment("TEST123")
        assertNotNull(found)
        assertEquals("TEST123", found.id)
        assertEquals("created", found.status)
    }

    @Test
    fun testFindMultipleShipments() {
        val shipment1 = Shipment("TEST1", "created")
        val shipment2 = Shipment("TEST2", "processing")
        val shipment3 = Shipment("TEST3", "shipped")

        TrackingSimulator.addShipment(shipment1)
        TrackingSimulator.addShipment(shipment2)
        TrackingSimulator.addShipment(shipment3)

        assertEquals("created", TrackingSimulator.findShipment("TEST1")?.status)
        assertEquals("processing", TrackingSimulator.findShipment("TEST2")?.status)
        assertEquals("shipped", TrackingSimulator.findShipment("TEST3")?.status)
    }

    @Test
    fun testFindNonExistentShipment() {
        val shipment = Shipment("TEST123", "created")
        TrackingSimulator.addShipment(shipment)

        val notFound = TrackingSimulator.findShipment("INVALID")
        assertNull(notFound)
    }

    @Test
    fun testRunSimulation() = runTest {
        val simulatorDeferred = async { TrackingSimulator.runSimulation(0L) }
        simulatorDeferred.await()

        val lostShipment = TrackingSimulator.findShipment("s10000")
        assertNotNull(lostShipment)
        assertEquals("lost", lostShipment.status)
        assertEquals("Los Angeles CA", lostShipment.currentLocation)
        assertEquals(Date(1652718051403), lostShipment.expectedDeliveryDate)
        assertEquals(3, lostShipment.getNotes().size)
        assertEquals(3, lostShipment.getUpdateHistory().size)
        assertEquals("created", lostShipment.getUpdateHistory()[0].previousStatus)
        assertEquals("shipped", lostShipment.getUpdateHistory()[1].previousStatus)
        assertEquals("delayed", lostShipment.getUpdateHistory()[2].previousStatus)
        assertEquals("lost", lostShipment.getUpdateHistory()[2].newStatus)

        val cancelledShipment = TrackingSimulator.findShipment("s10005")
        assertNotNull(cancelledShipment)
        assertEquals("canceled", cancelledShipment.status)

        val deliveredShipment = TrackingSimulator.findShipment("s10007")
        assertNotNull(deliveredShipment)
        assertEquals("delivered", deliveredShipment.status)
    }
}
  