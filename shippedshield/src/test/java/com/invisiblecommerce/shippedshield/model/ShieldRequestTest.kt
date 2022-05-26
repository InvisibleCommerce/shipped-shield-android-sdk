package com.invisiblecommerce.shippedshield.model

import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ShieldRequestTest {

    private val defaultOrderValue = BigDecimal.valueOf(129.99)

    private val request = ShieldRequest.Builder().setOrderValue(defaultOrderValue).build()
    private val emptyRequest = ShieldRequest.Builder().build()

    @Test
    fun testParams() {
        assertNotNull(request.toParamMap())
        assertEquals(ShieldRequest.Builder().setOrderValue(defaultOrderValue).build().toParamMap(), request.toParamMap())
    }

    @Test
    fun testEmptyParams() {
        assertNotNull(emptyRequest.toParamMap())
        assertEquals(ShieldRequest.Builder().build().toParamMap(), emptyRequest.toParamMap())
    }

    @Test
    fun testParamsMap() {
        val paramMap = request.toParamMap()
        assertEquals(
            mapOf(
                "order_value" to defaultOrderValue
            ),
            paramMap
        )
    }
}
