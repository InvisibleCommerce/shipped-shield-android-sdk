package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShippedError
import java.net.HttpURLConnection

class AuthenticationException(
    error: ShippedError
) : ShippedException(error, HttpURLConnection.HTTP_UNAUTHORIZED)