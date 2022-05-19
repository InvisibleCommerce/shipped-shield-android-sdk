package com.invisiblecommerce.shippedshield.model.parser

import org.json.JSONObject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ShieldErrorParserTest {

    @Test
    fun parserTest() {
        val parser = ShieldErrorParser()
        assertNotNull(parser.dateFormat)

        val jsonObject = JSONObject().put("error", "Auth error")
        val shieldError = parser.parse(jsonObject)
        assertEquals(shieldError.message, "Auth error")
    }
}
