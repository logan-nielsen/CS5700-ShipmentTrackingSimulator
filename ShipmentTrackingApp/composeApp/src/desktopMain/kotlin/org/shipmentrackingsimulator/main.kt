package org.shipmentrackingsimulator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.shipmentrackingsimulator.ui.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ShipmentTrackingSimulator",
    ) {
        App()
    }
}