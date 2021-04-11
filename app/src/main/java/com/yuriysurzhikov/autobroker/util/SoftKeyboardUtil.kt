package com.yuriysurzhikov.autobroker.util

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

object SoftKeyboardUtil {
    fun closeSoftKeyboard(view: View) {
        val inputMethodManager = view.context
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}