package com.shippedsuite.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class ShippedError(

    /**
     * Error code
     */
    val code: String? = null,

    /**
     * Name of the request parameter that caused the error
     */
    val source: String? = null,

    /**
     * Description of the error
     */
    val message: String? = null
) : ShippedModel, Parcelable, Serializable {

    override fun toString(): String {
        return "code $code, source $source, message $message"
    }
}
