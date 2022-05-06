package com.shippedsuite.shippedshield.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shippedsuite.shippedshield.APIRepository
import com.shippedsuite.shippedshield.ShippedAPIRepository
import com.shippedsuite.shippedshield.ShippedPlugins
import com.shippedsuite.shippedshield.databinding.ViewShieldFeeBannerBinding
import com.shippedsuite.shippedshield.log.Logger
import com.shippedsuite.shippedshield.model.ShieldRequest
import kotlinx.coroutines.*
import java.math.BigDecimal
import java.text.NumberFormat

class ShieldFeeBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var job: Job? = null

    private val apiRepository: APIRepository by lazy {
        ShippedAPIRepository()
    }

    private val binding by lazy {
        ViewShieldFeeBannerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.learnMore.paintFlags = binding.learnMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.learnMore.setOnClickListener {
            ShippedFeeDialog.show(context)
        }
        binding.shieldSwitch.isChecked = ShippedPlugins.shieldEnable
        binding.shieldSwitch.setOnCheckedChangeListener { _, isChecked ->
            ShippedPlugins.shieldEnable = isChecked
        }
    }

    fun updateOrderValue(orderValue: BigDecimal) {
        // Cancel job first
        cancelJob()

        job = CoroutineScope(Dispatchers.IO).launch {
            val result = runCatching {
                requireNotNull(
                    apiRepository.getShieldFee(
                        ShippedAPIRepository.ShieldRequestOptions(
                            request = ShieldRequest.Builder().setOrderValue(orderValue).build()
                        )
                    )
                )
            }
            withContext(Dispatchers.Main) {
                result.fold(
                    onSuccess = {
                        binding.fee.text = NumberFormat.getCurrencyInstance().format(it.shieldFee)
                    },
                    onFailure = {
                        Logger.error("Failed to get shield fee", it)
                    }
                )
            }
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
