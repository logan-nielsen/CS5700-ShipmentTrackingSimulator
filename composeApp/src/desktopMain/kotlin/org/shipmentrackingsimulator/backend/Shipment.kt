package backend

import java.util.Date

class Shipment(
    val id: String,
    status: String,
) {
    var expectedDeliveryDate: Date? = null
    var currentLocation: String? = null
    var status: String = status
        private set;
    private val notes = mutableListOf<String>()
    private val updateHistory = mutableListOf<ShippingUpdate>()
    private val shipmentObservers = mutableListOf<ShipmentObserver>()

    fun getNotes(): List<String> {
        return notes.toList()
    }

    fun getUpdateHistory(): List<ShippingUpdate> {
        return updateHistory.toList()
    }

    fun addNote(note: String) {
        notes.add(note)
    }

    fun addUpdate(newStatus: String, date: Date) {
        updateHistory.add(ShippingUpdate(newStatus, status, date))
        status = newStatus
    }

    fun registerObserver(shipmentObserver: ShipmentObserver) {
        shipmentObservers.add(shipmentObserver)
    }

    fun removeObserver(shipmentObserver: ShipmentObserver) {
        shipmentObservers.remove(shipmentObserver)
    }

    fun notifyObservers() {
        for (observer in shipmentObservers) {
            observer.update(this)
        }
    }
}