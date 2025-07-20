package org.shipmentrackingsimulator.shipments

import org.shipmentrackingsimulator.shipmentupdatestrategies.ShipmentUpdateStrategyFactory
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull

class ShipmentFactoryTest {
    @Test
    fun testCreate() {
        val strategies = arrayOf(
            "created",
            "shipped",
            "location",
            "delivered",
            "delayed",
            "lost",
            "canceled",
            "noteadded",
        )

        val factory = ShipmentUpdateStrategyFactory()
        for (strategy in strategies) {
            assertNotNull(factory.create(strategy))
        }
    }

    @Test
    fun testCreateInvalidType() {
        val factory = ShipmentUpdateStrategyFactory()
        assertFailsWith<IllegalArgumentException> {
            factory.create("invalid")
        }
        assertFailsWith<IllegalArgumentException> {
            factory.create("")
        }
    }
}
