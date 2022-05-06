package com.shippedsuite.shippedshield

import com.shippedsuite.shippedshield.model.Options
import com.shippedsuite.shippedshield.model.ShieldOffer

interface APIRepository {

    suspend fun getShieldFee(
        options: Options
    ): ShieldOffer
}
