package com.shippedsuite.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shippedsuite.shippedshield.ShippedAPIRepository
import com.shippedsuite.shippedshield.ShippedPlugins
import com.shippedsuite.shippedshield.log.Logger
import com.shippedsuite.shippedshield.model.ShieldRequest
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.debug("Logged from shippedshield.")

        ShippedPlugins.configure("pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112")

        GlobalScope.launch {
            var request = ShieldRequest.Builder().setOrderValue(BigDecimal.valueOf(129.99)).build()
            val response = ShippedAPIRepository().getShieldFee(request)
            Logger.debug(response.toString())
        }
    }
}