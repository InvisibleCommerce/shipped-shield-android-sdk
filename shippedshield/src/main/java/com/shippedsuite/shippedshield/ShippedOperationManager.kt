package com.shippedsuite.shippedshield

import com.shippedsuite.shippedshield.exception.APIException
import com.shippedsuite.shippedshield.exception.ShippedException
import com.shippedsuite.shippedshield.model.Options
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ShippedOperationManager(private val repository: APIRepository) : OperationManager {

    private val workContext = Dispatchers.Main

    override fun <T> startOperation(options: Options, listener: Shipped.ShippedListener<T>) {
        CoroutineScope(workContext).launch {
            execute(options, listener)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private suspend fun <T> execute(options: Options, listener: Shipped.ShippedListener<T>) {
        when (options) {
            is ShippedAPIRepository.ShieldRequestOptions -> {
                val result = runCatching {
                    requireNotNull(repository.getShieldFee(options))
                }
                withContext(Dispatchers.Main) {
                    result.fold(
                        onSuccess = { listener.onSuccess(it as T) },
                        onFailure = { listener.onFailed(handleError(it)) }
                    )
                }
            }
        }
    }

    private fun handleError(throwable: Throwable): ShippedException {
        return if (throwable is ShippedException) {
            throwable
        } else {
            APIException(message = throwable.message)
        }
    }
}
