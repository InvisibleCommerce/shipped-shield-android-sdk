package com.shippedsuite.shippedshield.model.parser

import com.shippedsuite.shippedshield.model.ShieldError
import com.shippedsuite.shippedshield.util.JsonUtils
import org.json.JSONObject

class ShieldErrorParser : ModelJsonParser<ShieldError> {

    override fun parse(json: JSONObject): ShieldError {
        return ShieldError(
            message = JsonUtils.optString(json, FIELD_ERROR)
        )
    }

    private companion object {
        private const val FIELD_ERROR = "error"
    }
}
