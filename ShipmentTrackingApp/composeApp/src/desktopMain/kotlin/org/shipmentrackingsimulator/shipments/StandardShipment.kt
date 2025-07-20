package org.shipmentrackingsimulator.shipments

import java.util.Date

class StandardShipment(id: String, status: String, creationDate: Date): Shipment(id, status, creationDate) {
    override fun validateDeliveryDate(): Boolean {
        return true
    }
}