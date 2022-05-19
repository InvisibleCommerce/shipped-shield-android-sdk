package com.invisiblecommerce.shippedshield.model

import org.junit.Test
import kotlin.test.assertEquals

class ShieldErrorTest {

    @Test
    fun testParams() {
        val error = ShieldErrorFixtures.Error
        assertEquals("Auth error", error.message)
    }
}