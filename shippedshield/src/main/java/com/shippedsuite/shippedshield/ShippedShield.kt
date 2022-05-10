package com.shippedsuite.shippedshield

import android.content.Context
import com.shippedsuite.shippedshield.exception.ShieldException
import com.shippedsuite.shippedshield.model.ShieldOffer
import com.shippedsuite.shippedshield.model.ShieldRequest
import java.math.BigDecimal

class ShippedShield internal constructor(
    private val operationManager: OperationManager,
) {

    interface Listener<T> {
        fun onSuccess(response: T)
        fun onFailed(exception: ShieldException)
    }

    constructor() : this(
        ShieldOperationManager(ShieldAPIRepository()),
    )

    fun getShieldFee(
        price: BigDecimal,
        listener: Listener<ShieldOffer>
    ) {
        operationManager.startOperation(
            ShieldAPIRepository.ShieldRequestOptions(
                request = ShieldRequest.Builder().setOrderValue(price).build()
            ),
            listener
        )
    }

    companion object {
        fun configurePublicKey(applicationContext: Context, publicKey: String) {
            ShieldPlugins.initialize(
                ShieldConfiguration.Builder(applicationContext, publicKey)
                    .enableLogging(false)
                    .setEnvironment(Mode.DEVELOPMENT)
                    .build()
            )
        }

        fun enableLogging(enable: Boolean) {
            ShieldPlugins.enableLogging = enable
        }

        fun setMode(environment: Mode) {
            ShieldPlugins.environment = environment
        }
    }
}
