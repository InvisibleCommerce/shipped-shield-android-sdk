package com.shippedsuite.example

import android.app.Application
import com.shippedsuite.shippedshield.Mode
import com.shippedsuite.shippedshield.ShippedShield

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Setup ShippedShield
        ShippedShield.configurePublicKey(
            this,
            "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        )

        // Optional, the default mode is development mode
        ShippedShield.setMode(Mode.DEVELOPMENT)
    }
}
