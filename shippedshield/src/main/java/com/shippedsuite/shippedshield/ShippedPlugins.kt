package com.shippedsuite.shippedshield

internal object ShippedPlugins {

    private var configuration: ShippedConfiguration =
        ShippedConfiguration("", false, Environment.PRODUCTION)

    fun initialize(configuration: ShippedConfiguration) {
        this.configuration = configuration
    }

    internal val publicKey: String
        get() {
            return configuration.publicKey
        }

    internal val enableLogging: Boolean
        get() {
            return configuration.enableLogging
        }

    internal val environment: Environment
        get() {
            return configuration.environment
        }
}
