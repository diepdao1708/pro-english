package com.example.proenglish.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setImageResource")
fun setImageResource(view: ImageView, imageResId: Int) {
    view.setImageResource(imageResId)
}

@BindingAdapter("visibilityView")
fun visibilityView(view: View, visible: Boolean) {
    view.visibility = if (visible) View.VISIBLE else View.GONE
}