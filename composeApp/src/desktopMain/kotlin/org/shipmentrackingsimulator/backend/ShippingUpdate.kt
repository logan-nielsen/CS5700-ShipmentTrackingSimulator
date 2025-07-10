package org.shipmentrackingsimulator.backend

import java.util.Date

class ShippingUpdate(
    val previousStatus: String,
    val newStatus: String,
    val date: Date,
)