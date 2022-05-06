package com.shippedsuite.shippedshield

import com.shippedsuite.shippedshield.exception.ShippedException
import com.shippedsuite.shippedshield.model.ShieldOffer
import com.shippedsuite.shippedshield.model.ShieldRequest
import java.math.BigDecimal

class Shipped internal constructor(
    private val shippedManager: OperationManager,
) {

    interface ShippedListener<T> {
        fun onSuccess(response: T)
        fun onFailed(exception: ShippedException)
    }

    constructor() : this(
        ShippedOperationManager(ShippedAPIRepository()),
    )

    fun getShieldFee(
        price: BigDecimal,
        listener: ShippedListener<ShieldOffer>
    ) {
        shippedManager.startOperation(
            ShippedAPIRepository.ShieldRequestOptions(
                request = ShieldRequest.Builder().setOrderValue(price).build()
            ),
            listener
        )
    }

    companion object {

        /**
         * Initialize some global configurations
         */
        fun initialize(configuration: ShippedConfiguration) {
            ShippedPlugins.initialize(configuration)
        }
    }
}
