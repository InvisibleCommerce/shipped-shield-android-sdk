package com.invisiblecommerce.shippedshield.exception

import com.invisiblecommerce.shippedshield.model.ShieldError
import org.junit.Test
import kotlin.test.assertEquals

class InvalidRequestExceptionTest {

    @Test
    fun testEquals() {
        val error = ShieldError(message = "message")
        val invalidRequestException = InvalidRequestException(
            param = "param",
            error
        )

        assertEquals("param", invalidRequestException.param)
        assertEquals("message", invalidRequestException.message)
    }
}
