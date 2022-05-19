package com.invisiblecommerce.shippedshield.model.parser

import org.json.JSONObject
import org.junit.Test
import java.math.BigDecimal
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ShieldOfferParserTest {

    @Test
    fun parserTest() {
        val jsonObject = JSONObject(
            """
        {
            "storefront_id": "test-paws.myshopify.com",
            "order_value": "129.99",
            "shield_fee": "2.27",
	        "offered_at": "2022-05-18T18:03:22.252-07:00"
        }
        """.trimIndent()
        )

        val parser = ShieldOfferParser()
        assertNotNull(parser.dateFormat)

        val offer = parser.parse(jsonObject)
        assertEquals(offer.storefrontId, "test-paws.myshopify.com")
        assertEquals(offer.orderValue, BigDecimal.valueOf(129.99))
        assertEquals(offer.shieldFee, BigDecimal.valueOf(2.27))
        assertEquals(offer.offeredAt, parser.dateFormat.parse("2022-05-18T18:03:22.252-07:00"))
    }
}