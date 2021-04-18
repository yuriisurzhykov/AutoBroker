package com.yuriysurzhikov.autobroker.ui.text

import android.text.Editable
import com.google.android.material.textfield.TextInputLayout

class ErrorTextWatcher(private val inputLayout: TextInputLayout) : SimpleTextWatcher() {
    override fun afterTextChanged(s: Editable?) {
        if (inputLayout.error != null) {
            inputLayout.error = null
        }
    }
}