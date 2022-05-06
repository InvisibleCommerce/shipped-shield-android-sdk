package com.shippedsuite.shippedshield.widget

import android.content.Context
import android.os.Bundle
import android.view.Window
import android.widget.Button
import androidx.appcompat.app.AppCompatDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.shippedsuite.shippedshield.R

class ShippedFeeDialog internal constructor(context: Context) :
    AppCompatDialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_shield_fee)
        findViewById<Button>(R.id.shipped_action_next)?.setOnClickListener { dismiss() }
    }

    companion object {
        fun show(context: Context) {
            val dialog = ShippedFeeDialog(context)
            dialog.show()
            dialog.window?.setLayout(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
            )
        }
    }
}
