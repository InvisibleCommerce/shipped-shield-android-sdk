package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShippedError

class APIException(
    error: ShippedError? = null,
    statusCode: Int = 0,
    message: String? = error?.message,
    e: Throwable? = null
) : ShippedException(error, statusCode, message, e)
