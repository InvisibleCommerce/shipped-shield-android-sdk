package com.shippedsuite.shippedshield

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

internal object ShippedPlugins {

    private const val SHARED_PREF = "shipped_sp"
    private const val SHIELD_ENABLE = "shield_enable"

    private lateinit var configuration: ShippedConfiguration

    private lateinit var sp: SharedPreferences

    fun initialize(configuration: ShippedConfiguration) {
        this.configuration = configuration
        this.sp = configuration.applicationContext.getSharedPreferences(
            SHARED_PREF,
            Context.MODE_PRIVATE
        )
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

    internal var shieldEnable: Boolean
        get() {
            return sp.getBoolean(SHIELD_ENABLE, true)
        }
        set(value) {
            sp.edit {
                putBoolean(SHIELD_ENABLE, value)
            }
        }
}
