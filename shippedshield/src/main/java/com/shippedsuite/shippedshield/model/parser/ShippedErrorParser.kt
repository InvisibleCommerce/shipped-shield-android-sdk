package com.shippedsuite.shippedshield.model.parser

import com.shippedsuite.shippedshield.model.ShippedError
import com.shippedsuite.shippedshield.util.JsonUtils
import org.json.JSONObject

class ShippedErrorParser: ModelJsonParser<ShippedError> {

    override fun parse(json: JSONObject): ShippedError {
        return ShippedError(
            code = JsonUtils.optString(json, FIELD_CODE),
            source = JsonUtils.optString(json, FIELD_SOURCE),
            message = JsonUtils.optString(json, FIELD_MESSAGE)
        )
    }

    private companion object {
        private const val FIELD_CODE = "error"
        private const val FIELD_SOURCE = "source"
        private const val FIELD_MESSAGE = "message"
    }
}
