package com.shippedsuite.shippedshield

import com.shippedsuite.shippedshield.model.Options
import com.shippedsuite.shippedshield.model.ShieldOffer
import com.shippedsuite.shippedshield.model.ShieldRequest

interface APIRepository {

    suspend fun getShieldFee(
        request: ShieldRequest
    ): ShieldOffer?
}