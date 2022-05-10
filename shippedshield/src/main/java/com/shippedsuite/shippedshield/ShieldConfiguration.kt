package com.shippedsuite.shippedshield

import android.content.Context

/**
`ShieldConfiguration` contains the base configuration the SDK needs.
 */
data class ShieldConfiguration internal constructor(
    val applicationContext: Context,
    val publicKey: String,
    var enableLogging: Boolean,
    var environment: Mode
) {
    class Builder(private val applicationContext: Context, private val publicKey: String) {

        private var enableLogging: Boolean = false

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
