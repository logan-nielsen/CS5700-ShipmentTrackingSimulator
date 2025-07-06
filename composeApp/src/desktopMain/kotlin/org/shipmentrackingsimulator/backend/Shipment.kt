package org.shipmentrackingsimulator.backend

import backend.ShipmentObserver
import backend.ShippingUpdate
import java.util.Date

class Shipment(
    val id: String,
    status: String,
) {
    var expectedDeliveryDate: Date? = null
        set(value) {
            field = value
            this.notifyObservers()
        }
    var currentLocation: String? = null
        set(value) {
            field = value
            this.notifyObservers()
        }
    var status: String = status
        private set;
    private val notes = mutableListOf<String>()
    private val updateHistory = mutableListOf<ShippingUpdate>()
    private val shipmentObservers = mutableListOf<ShipmentObserver>()

    init {
        this.notifyObservers()
    }

    fun getNotes(): List<String> {
        return notes.toList()
    }

    fun getUpdateHistory(): List<ShippingUpdate> {
        return updateHistory.toList()
    }

    fun addNote(note: String) {
        notes.add(note)
        this.notifyObservers()
    }

    fun addUpdate(newStatus: String, date: Date) {
        updateHistory.add(ShippingUpdate(newStatus, status, date))
        status = newStatus
        this.notifyObservers()
    }

    fun registerObserver(shipmentObserver: ShipmentObserver) {
        shipmentObservers.add(shipmentObserver)
        shipmentObserver.update(this)
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