package com.invisiblecommerce.shippedshield.exception

import com.invisiblecommerce.shippedshield.model.ShieldError
import com.invisiblecommerce.shippedshield.model.ShieldErrorFixtures
import org.junit.Test
import java.net.HttpURLConnection
import kotlin.test.assertEquals

class ShieldExceptionTest {

    @Test
    fun testEquals() {
        val permissionException = PermissionException(
            error = ShieldError()
        )
        assertEquals(permissionException.toString(), "com.invisiblecommerce.shippedshield.exception.PermissionException; status-code: 403, ShieldError(message=null)")
    }
}