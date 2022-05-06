package com.shippedsuite.shippedshield.widget

import android.content.Context
import android.graphics.Paint
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.shippedsuite.shippedshield.databinding.ViewShieldFeeBannerBinding
import com.shippedsuite.shippedshield.model.ShieldOffer
import java.text.NumberFormat

class ShieldFeeBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        ViewShieldFeeBannerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        binding.learnMore.paintFlags = binding.learnMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.learnMore.setOnClickListener {
        }
    }

    fun onResult(shieldOffer: ShieldOffer) {
        binding.fee.text = NumberFormat.getCurrencyInstance().format(shieldOffer.shieldFee)
    }
}
