package com.shippedsuite.shippedshield.exception

import com.shippedsuite.shippedshield.model.ShippedError
import java.net.HttpURLConnection

class PermissionException(
    error: ShippedError
) : ShippedException(error, HttpURLConnection.HTTP_FORBIDDEN)