package com.invisiblecommerce.shippedshield

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.exception.ShieldException
import com.invisiblecommerce.shippedshield.model.ShieldOffer
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class ShippedShieldTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun beforeTest(){
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }

    @After
    fun afterTest(){
        Dispatchers.resetMain()
    }

    @Test
    fun getShieldFeeTest() = runTest {
        val publicKey =
            "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        ShippedShield.configurePublicKey(context, publicKey)
        ShippedShield.enableLogging(true)
        ShippedShield.setMode(Mode.DEVELOPMENT)

        val defaultOrderValue = BigDecimal.valueOf(129.99)
        ShippedShield(UnconfinedTestDispatcher()).getShieldFee(
            defaultOrderValue,
            object : ShippedShield.Listener<ShieldOffer> {
                override fun onSuccess(response: ShieldOffer) {
                    assert(true)
                }

                override fun onFailed(exception: ShieldException) {
                    assert(false)
                }
            }
        )
    }
}
