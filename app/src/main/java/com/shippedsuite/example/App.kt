package com.shippedsuite.example

import android.app.Application
import com.shippedsuite.shippedshield.Mode
import com.shippedsuite.shippedshield.ShippedPlugins

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        ShippedPlugins.configure("pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112", enableLogging = true, mode = Mode.Development)
    }
}