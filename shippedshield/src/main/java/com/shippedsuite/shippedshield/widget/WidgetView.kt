package com.shippedsuite.shippedshield.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shippedsuite.shippedshield.APIRepository
import com.shippedsuite.shippedshield.ShieldAPIRepository
import com.shippedsuite.shippedshield.ShieldPlugins
import com.shippedsuite.shippedshield.databinding.ViewShieldFeeBannerBinding
import com.shippedsuite.shippedshield.exception.APIException
import com.shippedsuite.shippedshield.exception.ShieldException
import com.shippedsuite.shippedshield.model.ShieldRequest
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.text.NumberFormat

class WidgetView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    interface Callback<T> {
        fun onSuccess(isShieldEnabled: Boolean, shieldFee: T)
        fun onFailed(isShieldEnabled: Boolean, exception: ShieldException)
    }

    var callback: Callback<BigDecimal>? = null

    private var job: Job? = null

    private val apiRepository: APIRepository by lazy {
        ShieldAPIRepository()
    }

    private val binding by lazy {
        ViewShieldFeeBannerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.learnMore.paintFlags = binding.learnMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.learnMore.setOnClickListener {
            LearnMoreDialog.show(context)
        }
        binding.shieldSwitch.isChecked = ShieldPlugins.shieldEnable
        binding.shieldSwitch.setOnCheckedChangeListener { _, isChecked ->
            ShieldPlugins.shieldEnable = isChecked
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
                        callback?.onSuccess(ShieldPlugins.shieldEnable, it.shieldFee)
                        binding.fee.text = NumberFormat.getCurrencyInstance().format(it.shieldFee)
                    },
                    onFailure = {
                        callback?.onFailed(ShieldPlugins.shieldEnable, handleError(it))
                    }
                )
            }
        }
    }

    private fun handleError(throwable: Throwable): ShieldException {
        return if (throwable is ShieldException) {
            throwable
        } else {
            APIException(message = throwable.message)
        }
    }

    override fun onDetachedFromWindow() {
        cancelJob()
        super.onDetachedFromWindow()
    }

    private fun cancelJob() {
        job?.cancel()
        job = null
    }
}
