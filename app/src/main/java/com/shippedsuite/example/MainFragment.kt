package com.shippedsuite.example

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.shippedsuite.example.databinding.FragmentMainBinding
import com.shippedsuite.shippedshield.ShippedAPIRepository
import com.shippedsuite.shippedshield.log.Logger
import com.shippedsuite.shippedshield.model.ShieldRequest
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.NumberFormat

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
    }

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        activity?.runOnUiThread {
            // Show ERROR
            (activity as? MainActivity)?.setLoadingProgress(false)
            (activity as? MainActivity)?.showAlert("Error", throwable.localizedMessage ?: "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.learnMore.paintFlags = binding.learnMore.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.displayLearnMoreModel.setOnClickListener {

        }

        binding.sendShieldFeeRequest.setOnClickListener {
            (activity as? MainActivity)?.setLoadingProgress(true)
            viewLifecycleOwner.lifecycleScope.safeLaunch(
                Dispatchers.Main,
                coroutineExceptionHandler
            ) {
                val shieldOffer = withContext(Dispatchers.IO) {
                    val request = ShieldRequest.Builder().setOrderValue(
                        BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
                    ).build()
                    ShippedAPIRepository().getShieldFee(request)
                }

                Logger.debug(TAG, "response ${shieldOffer?.toString()}")

                (activity as? MainActivity)?.setLoadingProgress(false)
                shieldOffer?.shieldFee?.let {
                    binding.fee.text = NumberFormat.getCurrencyInstance().format(it)
                }
            }
        }
    }
}