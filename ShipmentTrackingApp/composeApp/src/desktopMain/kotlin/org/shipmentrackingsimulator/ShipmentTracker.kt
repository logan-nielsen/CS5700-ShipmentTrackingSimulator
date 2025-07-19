package org.shipmentrackingsimulator

import io.ktor.http.HttpHeaders
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receiveText
import io.ktor.server.response.respondText
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import org.shipmentrackingsimulator.shipments.Shipment
import org.shipmentrackingsimulator.shipmentupdatestrategies.ShipmentUpdateStrategyFactory
import java.util.Date

object ShipmentTracker {
    private val shipments = mutableListOf<Shipment>()

    fun findShipment(shipmentId: String?): Shipment? {
        if (shipmentId == null) return null

        return shipments.find { it.id == shipmentId }
    }

    fun addShipment(shipment: Shipment) {
        shipments.add(shipment)
    }

    fun reset() {
        shipments.clear()
    }

    fun run() {
        embeddedServer(Netty, port = 8080) {
            install(CORS) {
                allowHost("localhost:3000", schemes = listOf("http", "https"), subDomains = listOf("localhost"))
                allowHeader(HttpHeaders.ContentType)
            }

            routing {
                post("/api/updateShipment") {
                    val body = call.receiveText()
                    println("Received update: $body")
                    val items = body.split(",")
                    val (updateType, shipmentId, timestampOfUpdate) = items
                    val otherInfo = items.getOrNull(3)

                    val shipmentUpdateStrategyFactory = ShipmentUpdateStrategyFactory()
                    val shipmentUpdateStrategy = shipmentUpdateStrategyFactory.create(updateType)

                    shipmentUpdateStrategy.update(
                        shipmentId,
                        updateType,
                        Date(timestampOfUpdate.toLong()),
                        otherInfo
                    )

                    call.respondText("ok")
                }
            }
        }.start()
    }

}