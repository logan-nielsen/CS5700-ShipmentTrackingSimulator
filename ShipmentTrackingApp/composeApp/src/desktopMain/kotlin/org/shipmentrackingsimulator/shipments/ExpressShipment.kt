package org.shipmentrackingsimulator.shipments

import java.util.Calendar
import java.util.Date

class ExpressShipment(id: String, status: String, creationDate: Date): Shipment(id, status, creationDate) {
    private val maxDeliveryDays = 3

    override fun validateDeliveryDate(): Boolean {
        expectedDeliveryDate.let {
            if (it == null) { return false }

            val calendar = Calendar.getInstance()
            calendar.time = creationDate
            calendar.add(Calendar.DATE, maxDeliveryDays)
            val maxDate = calendar.time

            if (it <= maxDate) {
                return true
            } else {
                addNote("Shipment expected later than the maximum $maxDeliveryDays days waiting time for express shipments")
                return false
            }
        }
    }
}