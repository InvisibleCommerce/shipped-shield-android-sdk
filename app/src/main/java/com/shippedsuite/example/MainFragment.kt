package com.shippedsuite.example

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.shippedsuite.example.databinding.FragmentMainBinding
import com.shippedsuite.shippedshield.widget.LearnMoreDialog
import com.shippedsuite.shippedshield.widget.WidgetView
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import java.math.BigDecimal

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
    }

    private val defaultPrice = BigDecimal.valueOf(129.99)

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

        binding.widgetView.callback = object : WidgetView.Callback<BigDecimal> {
            override fun onResult(result: Map<String, Any>) {
                Log.d(TAG, "Widget response $result")
            }
        }

        binding.input.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.searchKey.value = binding.input.text?.trim()?.toString()
                    return@OnEditorActionListener true
                }
                false
            }
        )

        viewModel.searchKey
            .debounce(300)
            .distinctUntilChanged()
            .asLiveData()
            .observe(viewLifecycleOwner) {
                // Option 1: We support custom ui, you just need to pass the `order` params
                val price = try {
                    BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
                } catch (e: Exception) {
                    showAlert("Error", "Please input valid order price!")
                    return@observe
                }
                binding.widgetView.updateOrderValue(price)
            }

        binding.input.setText(defaultPrice.toString())

        viewModel.shieldLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is MainViewModel.ShieldOfferStatus.Success -> {
                    Log.d(TAG, "Get shield fee: $it")
                }
                is MainViewModel.ShieldOfferStatus.Fail -> {
                    Log.e(TAG, "Failed to get shield fee!", it.exception)
                }
            }
        }

        binding.displayLearnMoreModel.setOnClickListener {
            LearnMoreDialog.show(requireContext())
        }

        binding.sendShieldFeeRequest.setOnClickListener {
            val price = try {
                BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
            } catch (e: Exception) {
                showAlert("Error", "Please input valid order price!")
                return@setOnClickListener
            }
            // Option 2: We support api, so that you can use your own UI
            viewModel.getShieldFee(price)
        }
    }

    private fun showAlert(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(android.R.string.ok) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }
}
