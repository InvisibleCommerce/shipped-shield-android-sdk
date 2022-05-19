package com.invisiblecommerce.shippedshield.exception

import com.invisiblecommerce.shippedshield.model.ShieldError
import org.junit.Test
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class AuthenticationExceptionTest {

    @Test
    fun testEquals() {
        val authenticationException = AuthenticationException(
            error = ShieldError(
                message = "message"
            )
        )

        assertNotNull(authenticationException)
        assertEquals("message", authenticationException.message)
    }
}