package com.shippedsuite.shippedshield

import android.content.Context

data class ShieldConfiguration internal constructor(
    val applicationContext: Context,
    val publicKey: String,
    var enableLogging: Boolean,
    var environment: Mode
) {
    class Builder(private val applicationContext: Context, private val publicKey: String) {

        /**
         * You can set to true if you want to see more debug logs
         */
        private var enableLogging: Boolean = false

        /**
         * Set the environment to be used by Shipped
         */
        private var environment: Mode = Mode.DEVELOPMENT

        fun enableLogging(enable: Boolean): Builder = apply {
            this.enableLogging = enable
        }

        fun setEnvironment(environment: Mode): Builder = apply {
            this.environment = environment
        }

        fun build(): ShieldConfiguration {
            return ShieldConfiguration(
                applicationContext = applicationContext,
                publicKey = publicKey,
                enableLogging = enableLogging,
                environment = environment
            )
        }
    }
}
