package org.shipmentrackingsimulator.shipments

import org.shipmentrackingsimulator.ShipmentObserver
import org.shipmentrackingsimulator.ShippingUpdate
import java.util.Date

abstract class Shipment(
    val id: String,
    status: String,
    creationDate: Date,
) {
    var expectedDeliveryDate: Date? = null
        set(value) {
            field = value
            this.validateDeliveryDate()
            this.notifyObservers()
        }
    var currentLocation: String? = null
        set(value) {
            field = value
            this.notifyObservers()
        }
    var status: String = status
        private set
    private val notes = mutableListOf<String>()
    private val updateHistory = mutableListOf<ShippingUpdate>()
    private val shipmentObservers = mutableListOf<ShipmentObserver>()
    var creationDate = creationDate
        private set

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
        updateHistory.add(ShippingUpdate(status, newStatus, date))
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

    abstract fun validateDeliveryDate(): Boolean
}