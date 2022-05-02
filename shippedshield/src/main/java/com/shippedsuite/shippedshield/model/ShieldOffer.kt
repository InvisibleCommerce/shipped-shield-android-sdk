package com.shippedsuite.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
data class ShieldOffer constructor(
    var storefrontId: String? = null,

    var orderValue: BigDecimal? = null,

    var shieldFee: BigDecimal? = null,

    var offeredAt: Date? = null
): ShippedModel, Parcelable