package com.shippedsuite.example

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shippedsuite.example.databinding.FragmentMainBinding
import com.shippedsuite.shippedshield.widget.ShippedFeeDialog
import java.math.BigDecimal

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
    }

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(layoutInflater)
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            MainViewModel.Factory(
                requireActivity().application
            )
        )[MainViewModel::class.java]
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

        binding.displayLearnMoreModel.setOnClickListener {
            ShippedFeeDialog.show(requireContext())
        }

        binding.sendShieldFeeRequest.setOnClickListener {
            val price = try {
                BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
            } catch (e: Exception) {
                (activity as? MainActivity)?.showAlert(
                    "Error",
                    "Please input valid order price!"
                )
                return@setOnClickListener
            }

            // Option 1: We support custom ui, you just need to pass the `order` params
            binding.shieldFeeBanner.updateOrderValue(price)

            // Option 2: We support api, so that you can use your own UI
            viewModel.getShieldFee(price)
            viewModel.shieldFeeLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is MainViewModel.ShieldOfferStatus.Success -> {
                        Log.d(TAG, "Get shield fee success! $it")
                    }
                    is MainViewModel.ShieldOfferStatus.Fail -> {
                        Log.e(TAG, "Get shield fee failed!", it.exception)
                    }
                }
            }
        }
    }
}
