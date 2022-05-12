package com.invisiblecommerce.shippedshield

import android.content.Context
import com.invisiblecommerce.shippedshield.exception.ShieldException
import com.invisiblecommerce.shippedshield.model.ShieldOffer
import com.invisiblecommerce.shippedshield.model.ShieldRequest
import java.math.BigDecimal

/**
`ShippedShield` contains all the configuration and functionality provided by the SDK.
 */
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

    /**
    Get shield fee.

    @param orderValue An order value.
    @param listener A handler which includes shield fee.
     */
    fun getShieldFee(
        orderValue: BigDecimal,
        listener: Listener<ShieldOffer>
    ) {
        operationManager.startOperation(
            ShieldAPIRepository.ShieldRequestOptions(
                request = ShieldRequest.Builder().setOrderValue(orderValue).build()
            ),
            listener
        )
    }

    companion object {

        /**
        Configure public key.
         */
        fun configurePublicKey(applicationContext: Context, publicKey: String) {
            ShieldPlugins.initialize(
                ShieldConfiguration.Builder(applicationContext, publicKey)
                    .enableLogging(false)
                    .setEnvironment(Mode.DEVELOPMENT)
                    .build()
            )
        }

        /**
        Enable logging. False as default.
         */
        fun enableLogging(enable: Boolean) {
            ShieldPlugins.enableLogging = enable
        }

        /**
        Get sdk mode. Development mode as default.
         */
        fun setMode(environment: Mode) {
            ShieldPlugins.environment = environment
        }
    }
}
