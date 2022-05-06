package com.shippedsuite.example

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shippedsuite.shippedshield.Shipped
import com.shippedsuite.shippedshield.exception.ShippedException
import com.shippedsuite.shippedshield.model.ShieldOffer
import java.math.BigDecimal

internal class MainViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val shipped by lazy { Shipped() }

    val shieldFeeLiveData = MutableLiveData<ShieldOfferStatus>()

    sealed class ShieldOfferStatus {
        data class Success(val shieldOffer: ShieldOffer) : ShieldOfferStatus()
        data class Fail(val exception: ShippedException) : ShieldOfferStatus()
    }

    fun getShieldFee(price: BigDecimal) {
        shipped.getShieldFee(
            price,
            object : Shipped.ShippedListener<ShieldOffer> {
                override fun onSuccess(response: ShieldOffer) {
                    shieldFeeLiveData.value = ShieldOfferStatus.Success(response)
                }

                override fun onFailed(exception: ShippedException) {
                    shieldFeeLiveData.value = ShieldOfferStatus.Fail(exception)
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
