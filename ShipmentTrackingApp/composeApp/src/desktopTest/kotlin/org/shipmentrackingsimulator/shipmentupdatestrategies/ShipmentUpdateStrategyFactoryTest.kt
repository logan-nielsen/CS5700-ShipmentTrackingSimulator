package org.shipmentrackingsimulator.shipmentupdatestrategies

import org.shipmentrackingsimulator.shipments.ShipmentFactory
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ShipmentUpdateStrategyFactoryTest {
    @Test
    fun testCreate() {
        val shipmentFactory = ShipmentFactory()
        for (type in arrayOf("standard", "overnight", "express", "bulk")) {
            assertNotNull(shipmentFactory.create("TEST123", "created", type))
        }
    }

    @Test
    fun testCreateInvalidType() {
        val shipmentFactory = ShipmentFactory()
        assertFailsWith<IllegalArgumentException> {
            shipmentFactory.create("TEST123", "created", "invalid")
        }
        assertFailsWith<IllegalArgumentException> {
            shipmentFactory.create("TEST123", "created", "")
        }
    }
}