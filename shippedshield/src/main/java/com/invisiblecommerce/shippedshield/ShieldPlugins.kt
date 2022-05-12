package com.invisiblecommerce.shippedshield

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

internal object ShieldPlugins {

    private const val SHARED_PREF = "shipped_sp"
    private const val SHIELD_ENABLE = "shield_enable"

    private lateinit var configuration: ShieldConfiguration

    private lateinit var sp: SharedPreferences

    fun initialize(configuration: ShieldConfiguration) {
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

    internal var enableLogging: Boolean
        set(value) {
            configuration.enableLogging = value
        }
        get() {
            return configuration.enableLogging
        }

    internal var environment: Mode
        set(value) {
            configuration.environment = value
        }
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
