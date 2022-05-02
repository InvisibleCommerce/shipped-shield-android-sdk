package com.shippedsuite.shippedshield.model

interface ShippedRequestModel {
    fun toParamMap(): Map<String, Any>
}