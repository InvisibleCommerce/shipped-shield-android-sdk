package com.invisiblecommerce.shippedshield

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.exception.ShieldException
import com.invisiblecommerce.shippedshield.model.ShieldOffer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal
import java.util.concurrent.CountDownLatch

class MainViewModel: ViewModel() {

    private var success = false

    suspend fun getShieldFee(): Boolean {
        val defaultOrderValue = BigDecimal.valueOf(129.99)

        withContext(Dispatchers.IO) {
            ShippedShield().getShieldFee(
                defaultOrderValue,
                object : ShippedShield.Listener<ShieldOffer> {
                    override fun onSuccess(response: ShieldOffer) {
                        success = true
                    }

                    override fun onFailed(exception: ShieldException) {
                        success = false
                    }
                }
            )
        }

        return success
    }
}
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class ShippedShieldTest {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @Test
    fun getShieldFeeTest() = runBlocking {
        val mainViewModel = MainViewModel()

        val success = mainViewModel.getShieldFee()
        Assert.assertTrue(success)
    }
}