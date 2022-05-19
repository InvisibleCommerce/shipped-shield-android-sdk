package com.invisiblecommerce.shippedshield.model

import org.junit.Test
import java.math.BigDecimal
import java.util.*
import kotlin.test.assertEquals

class ShieldOfferTest {

    private val date = Date()

    private val offer = ShieldOffer(
        storefrontId = "1",
        orderValue = BigDecimal.valueOf(129.99),
        shieldFee = BigDecimal.valueOf(2.27),
        offeredAt = date
    )

    @Test
    fun testParams() {
        assertEquals("1", offer.storefrontId)
        assertEquals(BigDecimal.valueOf(129.99), offer.orderValue)
        assertEquals(BigDecimal.valueOf(2.27), offer.shieldFee)
        assertEquals(date, offer.offeredAt)
    }
}
