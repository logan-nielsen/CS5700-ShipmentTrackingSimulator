package org.shipmentrackingsimulator.shipmenttrackers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.shipmentrackingsimulator.shipmentupdatestrategies.ShipmentUpdateStrategyFactory
import java.text.SimpleDateFormat

object WebShipmentTracker: ShipmentTracker() {
    override fun run() {
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
                    val (updateType, shipmentId, updateDate) = items
                    val otherInfo = items.getOrNull(3)

                    val shipmentUpdateStrategyFactory = ShipmentUpdateStrategyFactory()
                    val shipmentUpdateStrategy = shipmentUpdateStrategyFactory.create(updateType)

                    val dateFormatter = SimpleDateFormat("yyyy-MM-dd")
                    shipmentUpdateStrategy.update(
                        shipmentId,
                        updateType,
                        dateFormatter.parse(updateDate),
                        otherInfo
                    )

                    call.respondText("ok")
                }
            }
        }.start()
    }
}