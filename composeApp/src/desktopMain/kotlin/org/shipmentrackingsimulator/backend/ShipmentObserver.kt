package backend

interface ShipmentObserver {
    fun update(shipment: Shipment)
}