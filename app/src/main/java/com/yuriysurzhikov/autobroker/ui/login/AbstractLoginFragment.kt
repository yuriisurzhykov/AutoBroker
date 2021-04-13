package com.yuriysurzhikov.autobroker.ui.login

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.yuriysurzhikov.autobroker.ui.AbstractFragment

abstract class AbstractLoginFragment : AbstractFragment() {

    protected lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(LoginViewModel::class.java)
    }
}