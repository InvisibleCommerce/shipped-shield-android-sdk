package com.invisiblecommerce.shippedshield.log

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.Mode
import com.invisiblecommerce.shippedshield.ShieldConfiguration
import com.invisiblecommerce.shippedshield.ShieldPlugins
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class LoggerTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun logTest() {
        val publicKey = "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        ShieldPlugins.initialize(
            ShieldConfiguration.Builder(context, publicKey)
                .enableLogging(true)
                .setEnvironment(Mode.DEVELOPMENT)
                .build()
        )

        Logger.verbose("verbose")
        Logger.debug("debug")
        Logger.info("info")
        Logger.warn("warn")
        Logger.error("error")
        assert(true)
    }
}
