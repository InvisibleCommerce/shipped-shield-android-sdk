package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShieldError

class InvalidRequestException(
    val param: String? = null,
    error: ShieldError? = null,
    statusCode: Int = 0,
    message: String? = error?.message,
    e: Throwable? = null
) : ShieldException(error, statusCode, message, e)
