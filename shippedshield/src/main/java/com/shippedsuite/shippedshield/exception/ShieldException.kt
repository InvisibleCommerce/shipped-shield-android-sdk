package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShieldError

abstract class ShieldException @JvmOverloads constructor(
    val error: ShieldError?,
    private val statusCode: Int,
    message: String? = error?.message,
    e: Throwable? = null
) : Exception(message, e) {

    override fun toString(): String {
        val statusCodeStr = "; status-code: $statusCode"
        return "${super.toString() + statusCodeStr}, $error"
    }
}
