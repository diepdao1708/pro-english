package com.example.proenglish.utils

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatDate(): String {
    return SimpleDateFormat("MMMM dd, yyyy ", Locale.US).format(this)
}

