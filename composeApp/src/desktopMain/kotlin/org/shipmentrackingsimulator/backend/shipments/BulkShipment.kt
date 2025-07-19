package org.shipmentrackingsimulator.backend.shipments

import java.util.Calendar

class BulkShipment(id: String, status: String): Shipment(id, status) {
    private val minDeliveryDays = 3

    override fun validateDeliveryDate(): Boolean {
        expectedDeliveryDate.let {
            if (it == null) { return false }

            val calendar = Calendar.getInstance()
            calendar.time = creationDate
            calendar.add(Calendar.DATE, minDeliveryDays)
            val minDate = calendar.time

            if (it <= minDate) {
                return true
            } else {
                addNote("Shipment expected sooner than the required $minDeliveryDays days waiting time for standard shipments")
                return false
            }
        }
    }
}