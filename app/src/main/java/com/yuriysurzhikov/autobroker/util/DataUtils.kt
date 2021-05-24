package com.yuriysurzhikov.autobroker.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.yuriysurzhikov.autobroker.BuildConfig

object DataUtils {

    private const val ARG_WAS_FIRST_LAUNCH = "is_first_app_launch"

    @JvmStatic
    fun isFirstOpening(context: Context): Boolean {
        val sharedPreferences =
            context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        return !sharedPreferences.getBoolean(ARG_WAS_FIRST_LAUNCH, false)
    }

    @JvmStatic
    fun writeWasFirstLaunch(context: Context) {
        val sharedPreferences =
            context.getSharedPreferences(BuildConfig.APPLICATION_ID, MODE_PRIVATE)
        sharedPreferences.edit()
            .putBoolean(ARG_WAS_FIRST_LAUNCH, true)
            .apply()
    }
}