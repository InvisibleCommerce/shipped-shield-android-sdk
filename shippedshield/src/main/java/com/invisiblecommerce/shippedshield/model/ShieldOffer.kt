package com.invisiblecommerce.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.util.*

@Parcelize
data class ShieldOffer constructor(
    val storefrontId: String,

    val orderValue: BigDecimal,

    val shieldFee: BigDecimal,

    val offeredAt: Date?
) : ShieldModel, Parcelable
