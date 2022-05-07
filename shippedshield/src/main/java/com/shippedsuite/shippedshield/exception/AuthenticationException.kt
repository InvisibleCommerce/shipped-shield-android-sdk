package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShieldError
import java.net.HttpURLConnection

class AuthenticationException(
    error: ShieldError
) : ShieldException(error, HttpURLConnection.HTTP_UNAUTHORIZED)
