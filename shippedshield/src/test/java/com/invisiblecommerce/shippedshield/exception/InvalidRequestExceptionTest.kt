package com.invisiblecommerce.shippedshield.exception

import org.junit.Test
import kotlin.test.assertEquals

class InvalidRequestExceptionTest {

    @Test
    fun testEquals() {
        val invalidRequestException = InvalidRequestException(
            param = "param",
            message = "message"
        )

        assertEquals("param", invalidRequestException.param)
        assertEquals("message", invalidRequestException.message)
    }
}