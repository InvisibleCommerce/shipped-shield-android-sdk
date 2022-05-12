package com.invisiblecommerce.shippedshield

import com.invisiblecommerce.shippedshield.model.Options

interface OperationManager {

    fun <T> startOperation(options: Options, listener: ShippedShield.Listener<T>)
}
