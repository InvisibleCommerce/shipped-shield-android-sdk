package com.invisiblecommerce.shippedshield.model.parser

import com.invisiblecommerce.shippedshield.model.ShieldOffer
import org.json.JSONObject
import java.math.BigDecimal

class ShieldOfferParser : ModelJsonParser<ShieldOffer> {

    override fun parse(json: JSONObject): ShieldOffer {
        return ShieldOffer(
            storefrontId = json.getString(STOREFRONT_ID),
            orderValue = BigDecimal.valueOf(json.getDouble(ORDER_VALUE)),
            shieldFee = BigDecimal.valueOf(json.getDouble(SHIELD_FEE)),
            offeredAt = dateFormat.parse(json.getString(OFFERED_AT))
        )
    }

    companion object {
        const val STOREFRONT_ID = "storefront_id"
        const val ORDER_VALUE = "order_value"
        const val SHIELD_FEE = "shield_fee"
        const val OFFERED_AT = "offered_at"
    }
}
