package org.shipmentrackingsimulator

import org.shipmentrackingsimulator.backend.ShipmentObserver
import kotlin.test.Test
import org.shipmentrackingsimulator.backend.Shipment
import java.util.Date
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ShipmentTest {
    @Test
    fun testShipmentInitialization() {
        val shipment = Shipment("TEST123", "created")
        assertEquals("TEST123", shipment.id)
        assertEquals("created", shipment.status)
        assertNull(shipment.expectedDeliveryDate)
        assertNull(shipment.currentLocation)
        assertTrue(shipment.getNotes().isEmpty())
        assertTrue(shipment.getUpdateHistory().isEmpty())
    }

    @Test
    fun testCurrentLocation() {
        val shipment = Shipment("TEST123", "created")
        assertNull(shipment.currentLocation)

        shipment.currentLocation = "New York"
        assertEquals("New York", shipment.currentLocation)

        shipment.currentLocation = "Los Angeles"
        assertEquals("Los Angeles", shipment.currentLocation)
    }

    @Test
    fun testAddNotes() {
        val shipment = Shipment("TEST123", "created")
        shipment.addNote("First note")
        shipment.addNote("Second note")
        assertEquals(2, shipment.getNotes().size)

        shipment.addNote("Third note")
        assertEquals(3, shipment.getNotes().size)

        assertEquals("First note", shipment.getNotes()[0])
        assertEquals("Second note", shipment.getNotes()[1])
        assertEquals("Third note", shipment.getNotes()[2])
    }

    @Test
    fun testStatusUpdates() {
        val shipment = Shipment("TEST123", "created")
        val date1 = Date()
        val date2 = Date(date1.time + 1000)
        val date3 = Date(date2.time + 1000)

        shipment.addUpdate("shipped", date1)
        shipment.addUpdate("delayed", date2)
        shipment.addUpdate("delivered", date3)

        assertEquals("delivered", shipment.status)
        assertEquals(3, shipment.getUpdateHistory().size)
        assertEquals("shipped", shipment.getUpdateHistory()[0].newStatus)
        assertEquals("delayed", shipment.getUpdateHistory()[1].newStatus)
        assertEquals("delivered", shipment.getUpdateHistory()[2].newStatus)
    }

    @Test
    fun testObservers() {
        var count1 = 0
        var count2 = 0
        val shipment = Shipment("TEST123", "created")

        val observer1 = object : ShipmentObserver {
            override fun update(shipment: Shipment) {
                count1++
            }
        }

        val observer2 = object : ShipmentObserver {
            override fun update(shipment: Shipment) {
                count2++
            }
        }

        shipment.registerObserver(observer1)
        shipment.registerObserver(observer2)

        assertEquals(1, count2)
        assertEquals(1, count1)

        shipment.addNote("Test note")
        assertEquals(2, count1)
        assertEquals(2, count2)

        shipment.currentLocation = "New York"
        assertEquals(3, count1)
        assertEquals(3, count2)

        shipment.expectedDeliveryDate = Date()
        assertEquals(4, count1)
        assertEquals(4, count2)

        shipment.removeObserver(observer1)

        shipment.addUpdate("delayed", Date())
        shipment.currentLocation = "Los Angeles"
        shipment.addNote("In transit")
        assertEquals(4, count1)
        assertEquals(7, count2)
    }
}