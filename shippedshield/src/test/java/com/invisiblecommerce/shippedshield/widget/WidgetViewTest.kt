package com.invisiblecommerce.shippedshield.widget

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.ShippedShield
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class WidgetViewTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val widgetView: WidgetView by lazy {
        WidgetView(context, null)
    }

    @Test
    fun widgetTest() {
        val publicKey = "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        ShippedShield.configurePublicKey(context, publicKey)

        val defaultOrderValue = BigDecimal.valueOf(129.99)
        widgetView.updateOrderValue(defaultOrderValue)
    }
}
