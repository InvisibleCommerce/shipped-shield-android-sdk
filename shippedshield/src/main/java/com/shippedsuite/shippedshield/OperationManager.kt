package com.shippedsuite.shippedshield

import com.shippedsuite.shippedshield.model.Options

interface OperationManager {

    fun <T> startOperation(options: Options, listener: Shipped.ShippedListener<T>)
}
