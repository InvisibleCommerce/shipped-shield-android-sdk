package com.shippedsuite.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shippedsuite.shippedshield.Logger

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.debug("Logged from shippedshield.")
    }
}