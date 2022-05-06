package com.shippedsuite.shippedshield.model

interface ShippedModel {
    override fun hashCode(): Int

    override fun equals(other: Any?): Boolean
}
