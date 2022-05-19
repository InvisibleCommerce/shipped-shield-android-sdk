package com.invisiblecommerce.shippedshield.http

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.invisiblecommerce.shippedshield.Mode
import com.invisiblecommerce.shippedshield.ShieldPlugins
import com.invisiblecommerce.shippedshield.ShippedShield
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class HttpRequestTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    init {
        val publicKey = "pk_development_117c2ee46c122fb0ce070fbc984e6a4742040f05a1c73f8a900254a1933a0112"
        ShippedShield.configurePublicKey(context, publicKey)
    }

    @Test
    fun contentTypeTest() {
        val contentType = HttpRequest.createGet(
            ShieldPlugins.environment.baseUrl()
        ).contentType
        assertEquals("application/json; charset=UTF-8", contentType)
    }

    @Test
    fun headersTest() {
        val headers = HttpRequest.createGet(
            ShieldPlugins.environment.baseUrl()
        ).headers
        assertEquals("Bearer ${ShieldPlugins.publicKey}", headers["Authorization"])
    }

    @Test
    fun urlTest() {
        val url = HttpRequest.createGet(
            url = ShieldPlugins.environment.baseUrl()
        ).url

        assertEquals(Mode.DEVELOPMENT.baseUrl(), url)
    }

    @Test
    fun equalTest() {
        val params = mapOf("aaa" to "123")

        assertEquals(
            HttpRequest.createPost(
                ShieldPlugins.environment.baseUrl(),
                params
            ).toString(),
            HttpRequest.createPost(
                ShieldPlugins.environment.baseUrl(),
                params
            ).toString()
        )
    }
}
