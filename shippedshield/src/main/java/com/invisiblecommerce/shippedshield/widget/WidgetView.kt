package com.invisiblecommerce.shippedshield.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.invisiblecommerce.shippedshield.APIRepository
import com.invisiblecommerce.shippedshield.ShieldAPIRepository
import com.invisiblecommerce.shippedshield.ShieldPlugins.shieldEnable
import com.invisiblecommerce.shippedshield.databinding.ViewShieldWidgetBinding
import com.invisiblecommerce.shippedshield.exception.APIException
import com.invisiblecommerce.shippedshield.exception.ShieldException
import com.invisiblecommerce.shippedshield.model.ShieldOffer
import com.invisiblecommerce.shippedshield.model.ShieldRequest
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.text.NumberFormat

/**
A widget view which shows the shield fee.
 */
class WidgetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val IS_SHIELD_ENABLE_KEY = "isShieldEnabled"
        const val SHIELD_FEE_KEY = "shieldFee"
        const val ERROR_KEY = "error"
    }

    interface Callback<T> {
        fun onResult(result: Map<String, Any>)
    }

    var callback: Callback<BigDecimal>? = null

    private var job: Job? = null

    private var cacheResult: MutableMap<String, Any> = mutableMapOf()

    private val apiRepository: APIRepository by lazy {
        ShieldAPIRepository()
    }

    private val binding by lazy {
        ViewShieldWidgetBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.learnMore.paintFlags = binding.learnMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.learnMore.setOnClickListener {
            LearnMoreDialog.show(context)
        }
        binding.shieldSwitch.isChecked = shieldEnable
        binding.shieldSwitch.setOnCheckedChangeListener { _, isChecked ->
            shieldEnable = isChecked
            onResult()
        }
    }

    fun updateOrderValue(orderValue: BigDecimal) {
        // Cancel job first
        cancelJob()

        job = CoroutineScope(Dispatchers.IO).launch {
            val result = runCatching {
                requireNotNull(
                    apiRepository.getShieldFee(
                        ShieldAPIRepository.ShieldRequestOptions(
                            request = ShieldRequest.Builder().setOrderValue(orderValue).build()
                        )
                    )
                )
            }
            withContext(Dispatchers.Main) {
                result.fold(
                    onSuccess = {
                        onResult(shieldOffer = it)
                        binding.fee.text = NumberFormat.getCurrencyInstance().format(it.shieldFee)
                    },
                    onFailure = {
                        onResult(error = handleError(it))
                    }
                )
            }
        }
    }

    private fun onResult(shieldOffer: ShieldOffer? = null, error: ShieldException? = null) {
        when {
            shieldOffer != null -> {
                cacheResult = mutableMapOf(SHIELD_FEE_KEY to shieldOffer.shieldFee)
            }
            error != null -> {
                cacheResult = mutableMapOf(ERROR_KEY to error)
            }
        }
        cacheResult[IS_SHIELD_ENABLE_KEY] = shieldEnable
        callback?.onResult(cacheResult)
    }

    private fun handleError(throwable: Throwable): ShieldException {
        return if (throwable is ShieldException) {
            throwable
        } else {
            APIException(message = throwable.message)
        }
    }

    override fun onDetachedFromWindow() {
        cacheResult.clear()
        cancelJob()
        super.onDetachedFromWindow()
    }

    private fun cancelJob() {
        job?.cancel()
        job = null
    }
}
