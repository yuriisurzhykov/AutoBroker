package com.yuriysurzhikov.autobroker.ui.navigtor

import android.content.Context
import androidx.fragment.app.Fragment
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener

interface IBaseNavigator<T> : OnItemClickListener<T> {
    fun attach(context: Context, fragment: Fragment)
    fun detach()
}