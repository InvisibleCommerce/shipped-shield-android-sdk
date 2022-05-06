package com.shippedsuite.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.shippedsuite.example.databinding.FragmentMainBinding
import java.math.BigDecimal

class MainFragment : Fragment() {

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
            (activity as? MainActivity)?.setLoadingProgress(true)
            viewModel.getShieldFee(price)
        }

        viewModel.shieldFeeLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainViewModel.ShieldOfferStatus.Success -> {
                    (activity as? MainActivity)?.setLoadingProgress(false)
                    binding.shieldFeeBanner.onResult(it.shieldOffer)
                }
                is MainViewModel.ShieldOfferStatus.Fail -> {
                    (activity as? MainActivity)?.setLoadingProgress(false)
                    (activity as? MainActivity)?.showAlert(
                        "Error",
                        it.exception.message ?: "Unknown error"
                    )
                }
            }
        }
    }
}
