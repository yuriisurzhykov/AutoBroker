package com.yuriysurzhikov.autobroker.util

import android.content.Context
import android.view.View

object ViewUtils {

    fun setVisible(view: View) {
        if (view.visibility != View.VISIBLE) {
            view.visibility = View.VISIBLE
        }
    }

    fun setInvisible(view: View) {
        if (view.visibility != View.INVISIBLE) {
            view.visibility = View.INVISIBLE
        }
    }

    fun setGone(view: View) {
        if (view.visibility != View.GONE) {
            view.visibility = View.GONE
        }
    }

    fun dpToPx(dp: Int, context: Context): Float {
        return context.resources.displayMetrics.density * dp
    }
}