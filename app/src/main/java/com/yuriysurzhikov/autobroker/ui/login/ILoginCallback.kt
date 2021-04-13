package com.yuriysurzhikov.autobroker.ui.login

import com.yuriysurzhikov.autobroker.model.entity.User

interface ILoginCallback {
    fun openOnBoarding()
    fun onLoginSuccess(user: User)
}