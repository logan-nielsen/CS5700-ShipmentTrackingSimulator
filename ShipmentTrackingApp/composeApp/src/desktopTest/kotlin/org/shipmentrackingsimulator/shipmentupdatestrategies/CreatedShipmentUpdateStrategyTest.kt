package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipmenttrackers.WebShipmentTracker
import java.util.Date
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CreatedShipmentUpdateStrategyTest {
    @BeforeTest
    fun resetTrackingSimulator() {
        WebShipmentTracker.reset()
    }

    @Test
    fun testCreatedShipmentStrategy() {
        val shipmentTypes = arrayOf("standard", "overnight", "express", "bulk")
        for (type in shipmentTypes) {
            val strategy = CreatedShipmentUpdateStrategy()
            strategy.update("TEST123", "created", Date(1652718051403), type)

            val shipment = WebShipmentTracker.findShipment("TEST123")
            assertNotNull(shipment)
            assertEquals("created", shipment.status)
            assertNull(shipment.expectedDeliveryDate)
            assertNull(shipment.currentLocation)
            assertEquals(0, shipment.getNotes().size)
            assertEquals(0, shipment.getUpdateHistory().size)
        }
    }
    }