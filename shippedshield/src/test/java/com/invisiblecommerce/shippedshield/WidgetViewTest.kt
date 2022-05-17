package com.invisiblecommerce.shippedshield

import android.content.Context
import android.view.ContextThemeWrapper
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.widget.WidgetView
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
        val defaultOrderValue = BigDecimal.valueOf(129.99)
        widgetView.updateOrderValue(defaultOrderValue)
    }
}