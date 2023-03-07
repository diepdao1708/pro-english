package com.example.proenglish.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setImageResource")
fun setImageResource(view: ImageView, imageResId: Int) {
    view.setImageResource(imageResId)
}