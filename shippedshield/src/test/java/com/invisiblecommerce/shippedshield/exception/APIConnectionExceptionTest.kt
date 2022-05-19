package com.invisiblecommerce.shippedshield.exception

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class APIConnectionExceptionTest {

    @Test
    fun testEquals() {
        val apiException = APIConnectionException(
            message = "error",
            e = Exception()
        )

        assertNotNull(apiException)
        assertEquals("error", apiException.message)
    }
}