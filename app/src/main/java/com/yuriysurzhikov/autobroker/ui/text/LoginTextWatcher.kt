package com.yuriysurzhikov.autobroker.ui.text

import android.os.Handler
import android.os.Looper
import android.text.Editable
import com.google.android.material.textfield.TextInputLayout
import com.yuriysurzhikov.autobroker.util.Const.LOGIN_TEXT_TIMER

class LoginTextWatcher constructor(val task: Runnable) :
    SimpleTextWatcher() {

    private val handler = Handler(Looper.getMainLooper())

    override fun afterTextChanged(s: Editable?) {
        handler.removeCallbacks(task)
        handler.postDelayed(task, LOGIN_TEXT_TIMER)
    }
}