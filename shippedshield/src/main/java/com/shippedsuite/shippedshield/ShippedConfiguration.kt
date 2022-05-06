package com.shippedsuite.shippedshield

import android.content.Context

data class ShippedConfiguration internal constructor(
    val applicationContext: Context,
    val publicKey: String,
    val enableLogging: Boolean,
    val environment: Environment
) {
    class Builder(val applicationContext: Context, private val publicKey: String) {

        /**
         * You can set to true if you want to see more debug logs
         */
        private var enableLogging: Boolean = false

        /**
         * Set the environment to be used by Shipped
         */
        private var environment: Environment = Environment.PRODUCTION

        fun enableLogging(enable: Boolean): Builder = apply {
            this.enableLogging = enable
        }

        fun setEnvironment(environment: Environment): Builder = apply {
            this.environment = environment
        }

        fun build(): ShippedConfiguration {
            return ShippedConfiguration(
                applicationContext = applicationContext,
                publicKey = publicKey,
                enableLogging = enableLogging,
                environment = environment
            )
        }
    }
}
