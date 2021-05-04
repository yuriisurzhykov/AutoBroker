package com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar

interface IStyleFragment {
    @DrawableRes
    fun getNavigationIcon(): Int

    @ColorRes
    fun getToolbarColor(): Int

    @StringRes
    fun getTitleRes(): Int

    fun getTitle(): String?

    fun getToolbar(): Toolbar?
}