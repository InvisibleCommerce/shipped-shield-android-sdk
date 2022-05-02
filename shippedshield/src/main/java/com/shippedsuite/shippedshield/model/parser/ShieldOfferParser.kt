package com.shippedsuite.shippedshield.model.parser

import com.shippedsuite.shippedshield.model.ShieldOffer
import com.shippedsuite.shippedshield.util.JsonUtils
import org.json.JSONObject
import java.math.BigDecimal

class ShieldOfferParser : ModelJsonParser<ShieldOffer> {

    override fun parse(json: JSONObject): ShieldOffer {
        return ShieldOffer(
            storefrontId = JsonUtils.optString(json, STOREFRONT_ID),
            orderValue = JsonUtils.optDouble(json, ORDER_VALUE)?.let {
                BigDecimal.valueOf(it)
            },
            shieldFee = JsonUtils.optDouble(json, SHIELD_FEE)?.let {
                BigDecimal.valueOf(it)
            },
            offeredAt = JsonUtils.optString(json, OFFERED_AT)?.let {
                dateFormat.parse(it)
            }
        )
    }

    companion object {
        const val STOREFRONT_ID = "storefront_id"
        const val ORDER_VALUE = "order_value"
        const val SHIELD_FEE = "shield_fee"
        const val OFFERED_AT = "offered_at"
    }
}