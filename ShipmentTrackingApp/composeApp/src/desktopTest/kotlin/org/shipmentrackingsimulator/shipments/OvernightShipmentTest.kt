package org.shipmentrackingsimulator.shipments

import java.util.Calendar
import kotlin.test.Test
import kotlin.test.assertEquals

class OvernightShipmentTest {
    @Test
    fun testOvernightShipmentOutsideOfDeliveryWindow() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "overnight")

        val calendar = Calendar.getInstance()
        for (deliveryDays in 0..2) {
            calendar.time = shipment.creationDate
            calendar.add(Calendar.DATE, deliveryDays)
            shipment.expectedDeliveryDate = calendar.time
            println(calendar.time)
        }

        assertEquals(1, shipment.getNotes().size)
    }
}
