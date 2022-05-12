package com.invisiblecommerce.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ShieldError(

    /**
     * Error message
     */
    val message: String? = null
) : ShieldModel, Parcelable, Serializable
