package com.shippedsuite.example

import android.app.Application
import com.shippedsuite.shippedshield.Mode
import com.shippedsuite.shippedshield.ShippedShield

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        ShippedShield.configurePublicKey(
            this,
            "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        )

        // Optional
        // Enable sdk logs, need to set to false on production (default false)
        ShippedShield.enableLogging(true)
        // Set sdk mode, set to development to debug (default production)
        ShippedShield.setMode(Mode.DEVELOPMENT)
    }
}
