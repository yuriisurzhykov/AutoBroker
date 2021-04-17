package com.yuriysurzhikov.autobroker.ui.login

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class AbstractLoginFragment : AbstractFragment() {

    protected lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(activity!!).get(LoginViewModel::class.java)
    }

    protected fun showMessage(message: String?) {
        Snackbar.make(view!!, message ?: "", Snackbar.LENGTH_LONG).show()
    }
}