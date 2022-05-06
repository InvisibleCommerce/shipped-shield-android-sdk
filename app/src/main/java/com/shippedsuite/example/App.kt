package com.shippedsuite.example

import android.app.Application
import com.shippedsuite.shippedshield.Environment
import com.shippedsuite.shippedshield.Shipped
import com.shippedsuite.shippedshield.ShippedConfiguration

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Shipped.initialize(
            ShippedConfiguration.Builder("pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112")
                .enableLogging(true)
                .setEnvironment(Environment.DEVELOPMENT)
                .build()
        )
    }
}
