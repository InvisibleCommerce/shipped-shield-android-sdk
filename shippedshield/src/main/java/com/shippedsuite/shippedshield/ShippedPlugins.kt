package com.shippedsuite.shippedshield

object ShippedPlugins {

    internal var publicKey: String? = null

    internal var enableLogging: Boolean = false

    internal var mode: Mode = Mode.Development

    fun configure(publicKey: String, enableLogging: Boolean = false, mode: Mode = Mode.Development) {
        this.publicKey = publicKey
        this.enableLogging = enableLogging
        this.mode = mode
    }
}