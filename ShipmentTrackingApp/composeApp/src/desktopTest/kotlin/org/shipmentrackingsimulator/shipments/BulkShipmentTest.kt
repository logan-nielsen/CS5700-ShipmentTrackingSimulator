package org.shipmentrackingsimulator.shipments

import java.util.Calendar
import kotlin.test.Test
import kotlin.test.assertEquals

class BulkShipmentTest {
    @Test
    fun testBulkShipmentOutsideOfDeliveryWindow() {
        val shipmentFactory = ShipmentFactory()
        val shipment = shipmentFactory.create("TEST123", "created", "bulk")

        val calendar = Calendar.getInstance()
        for (deliveryDays in 0..4) {
            calendar.time = shipment.creationDate
            calendar.add(Calendar.DATE, deliveryDays)
            shipment.expectedDeliveryDate = calendar.time
            println(calendar.time)
        }

        assertEquals(3, shipment.getNotes().size)
    }
}
