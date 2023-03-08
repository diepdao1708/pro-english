package com.example.proenglish.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import com.example.proenglish.R

object LoadingUtils {

    private var dialog: Dialog? = null
    fun showLoading(context: Context, cancelable: Boolean = false): Dialog {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.loading)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(cancelable)

        dialog.show()
        this.dialog = dialog
        return dialog

    }

    fun hideLoading() {
        dialog?.dismiss()
        dialog = null
    }
}