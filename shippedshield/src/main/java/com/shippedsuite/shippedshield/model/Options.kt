package com.shippedsuite.shippedshield.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
open class Options constructor(
    open val publicKey: String
) : Parcelable