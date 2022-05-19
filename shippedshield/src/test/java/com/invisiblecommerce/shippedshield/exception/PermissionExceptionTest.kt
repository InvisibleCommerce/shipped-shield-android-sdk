package com.invisiblecommerce.shippedshield.exception

import com.invisiblecommerce.shippedshield.model.ShieldError
import org.junit.Test
import kotlin.test.assertNotNull

class PermissionExceptionTest {

    @Test
    fun testEquals() {
        val permissionException = PermissionException(
            error = ShieldError()
        )
        assertNotNull(permissionException)
    }
}
