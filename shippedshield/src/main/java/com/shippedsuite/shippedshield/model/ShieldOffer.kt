package com.shippedsuite.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
data class ShieldOffer constructor(
    val storefrontId: String? = null,

    val orderValue: BigDecimal? = null,

    val shieldFee: BigDecimal? = null,

    val offeredAt: Date? = null
): ShippedModel, Parcelable