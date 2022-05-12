package com.invisiblecommerce.shippedshield

import com.invisiblecommerce.shippedshield.model.Options
import com.invisiblecommerce.shippedshield.model.ShieldOffer

interface APIRepository {

    suspend fun getShieldFee(
        options: Options
    ): ShieldOffer
}
