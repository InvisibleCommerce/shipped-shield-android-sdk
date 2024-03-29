package com.shippedsuite.example

import android.app.AlertDialog
import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.shippedsuite.example.databinding.FragmentMainBinding
import com.invisiblecommerce.shippedshield.widget.LearnMoreDialog
import com.invisiblecommerce.shippedshield.widget.WidgetView
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import java.math.BigDecimal

class MainFragment : Fragment() {

    companion object {
        const val TAG = "MainFragment"
    }

    private val defaultOrderValue = BigDecimal.valueOf(129.99)

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

        // WidgetView callback
        binding.widgetView.callback = object : WidgetView.Callback<BigDecimal> {
            override fun onResult(result: Map<String, Any>) {
                Log.d(TAG, "Widget response $result")
            }
        }

        binding.input.setOnEditorActionListener(
            OnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.searchKey.value = binding.input.text?.trim()?.toString()
                    val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.input.windowToken, 0)
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
                val orderValue = try {
                    BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
                } catch (e: Exception) {
                    showAlert("Invalid amount", "Please input a valid amount for order value.")
                    return@observe
                }

                // Update order value
                binding.widgetView.updateOrderValue(orderValue)
            }

        binding.input.setText(defaultOrderValue.toString())

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

        // Display learn more model manually
        binding.displayLearnMoreModel.setOnClickListener {
            LearnMoreDialog.show(requireContext())
        }

        // Get shield fee manually
        binding.sendShieldFeeRequest.setOnClickListener {
            val orderValue = try {
                BigDecimal.valueOf(binding.input.text.trim().toString().toDouble())
            } catch (e: Exception) {
                showAlert("Invalid amount", "Please input a valid amount for order value.")
                return@setOnClickListener
            }
            viewModel.getShieldFee(orderValue)
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
