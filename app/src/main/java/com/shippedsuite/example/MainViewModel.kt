package com.shippedsuite.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shippedsuite.shippedshield.ShippedShield
import com.shippedsuite.shippedshield.exception.ShieldException
import com.shippedsuite.shippedshield.model.ShieldOffer
import kotlinx.coroutines.flow.MutableStateFlow
import java.math.BigDecimal

internal class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val shield by lazy { ShippedShield() }

    val shieldLiveData = MutableLiveData<ShieldOfferStatus>()

    val searchKey: MutableStateFlow<String?> = MutableStateFlow(null)

    sealed class ShieldOfferStatus {
        data class Success(val shieldOffer: ShieldOffer) : ShieldOfferStatus()
        data class Fail(val exception: ShieldException) : ShieldOfferStatus()
    }

    fun getShieldFee(orderValue: BigDecimal) {
        shield.getShieldFee(
            orderValue,
            object : ShippedShield.Listener<ShieldOffer> {
                override fun onSuccess(response: ShieldOffer) {
                    shieldLiveData.value = ShieldOfferStatus.Success(response)
                }

                override fun onFailed(exception: ShieldException) {
                    shieldLiveData.value = ShieldOfferStatus.Fail(exception)
                }
            }
        )
    }

    internal class Factory(
        private val application: Application
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(
                application
            ) as T
        }
    }
}
