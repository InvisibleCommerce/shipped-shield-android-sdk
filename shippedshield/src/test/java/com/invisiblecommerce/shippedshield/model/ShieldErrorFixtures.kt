package com.invisiblecommerce.shippedshield.model

import com.invisiblecommerce.shippedshield.model.parser.ShieldErrorParser
import org.json.JSONObject

internal object ShieldErrorFixtures {

    val Error: ShieldError = ShieldErrorParser().parse(
        JSONObject(
            """
        {
            "error": "Auth error"
        }
            """.trimIndent()
        )
    )
}