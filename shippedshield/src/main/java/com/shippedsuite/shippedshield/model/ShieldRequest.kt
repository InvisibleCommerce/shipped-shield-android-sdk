package com.shippedsuite.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class ShieldRequest internal constructor(

    var orderValue: BigDecimal? = null
) : ShippedRequestModel, Parcelable {

    @IgnoredOnParcel
    val path: String = "/v1/shield_offers"

    private companion object {
        private const val ORDER_VALUE = "order_value"
    }

    override fun toParamMap(): Map<String, Any> {
        return mapOf<String, Any>()
            .plus(
                orderValue?.let {
                    mapOf(ORDER_VALUE to it)
                }.orEmpty()
            )
    }

    class Builder : ObjectBuilder<ShieldRequest> {
        private var orderValue: BigDecimal? = null

        fun setOrderValue(orderValue: BigDecimal?): Builder = apply {
            this.orderValue = orderValue
        }

        override fun build(): ShieldRequest {
            return ShieldRequest(
                orderValue = orderValue
            )
        }
    }
}
