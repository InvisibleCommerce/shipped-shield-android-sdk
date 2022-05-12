package com.invisiblecommerce.shippedshield.exception

import com.invisiblecommerce.shippedshield.model.ShieldError
import java.net.HttpURLConnection

class AuthenticationException(
    error: ShieldError
) : ShieldException(error, HttpURLConnection.HTTP_UNAUTHORIZED)
