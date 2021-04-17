package com.yuriysurzhikov.autobroker.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentRegistrationUserDataBinding
import com.yuriysurzhikov.autobroker.ui.main.MainActivity

class UserDataFragment : AbstractLoginFragment() {

    private lateinit var binding: FragmentRegistrationUserDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerButton.setOnClickListener {
            attemptRegistration()
        }
    }

    private fun attemptRegistration() {
        if (checkAllField()) {
            Intent(activity, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }.also {
                startActivity(it)
            }
        }
    }

    private fun checkAllField(): Boolean {
        return false
    }

    companion object {
        fun newInstance() = UserDataFragment()
    }
}