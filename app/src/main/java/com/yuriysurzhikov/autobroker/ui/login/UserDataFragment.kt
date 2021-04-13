package com.yuriysurzhikov.autobroker.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.main.MainActivity

class UserDataFragment : AbstractLoginFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_registration_user_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.register_button)?.setOnClickListener {
            attemptRegistration()
        }
    }

    private fun attemptRegistration() {
        Intent(activity, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }.also {
            startActivity(it)
        }
    }
}