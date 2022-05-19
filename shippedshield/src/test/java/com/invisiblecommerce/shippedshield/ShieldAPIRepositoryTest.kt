package com.invisiblecommerce.shippedshield

import com.invisiblecommerce.shippedshield.model.ShieldRequest
import org.junit.Assert
import org.junit.Test
import java.math.BigDecimal

class ShieldAPIRepositoryTest {

    @Test
    fun createShieldFeeRequestTest() {
        val defaultOrderValue = BigDecimal.valueOf(129.99)
        val request = ShieldRequest.Builder().setOrderValue(defaultOrderValue).build()
        Assert.assertNotEquals(request, null)
        Assert.assertNotEquals(request.toParamMap(), null)
        Assert.assertEquals(request.orderValue, defaultOrderValue)

        val url = ShieldAPIRepository.createShieldOffersUrl(Mode.DEVELOPMENT.baseUrl())
        Assert.assertNotEquals(url, null)

        val options = ShieldAPIRepository.ShieldRequestOptions(request)
        Assert.assertEquals(options.request, request)
    }
}
