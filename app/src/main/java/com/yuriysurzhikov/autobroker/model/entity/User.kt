package com.yuriysurzhikov.autobroker.model.entity

import android.net.Uri

data class User(
    var strId: String,
    var displayName: String,
    var phone: String?,
    var email: String?,
    var photoUrl: Uri?,
    var location: UserLocation,
    var fullRegistration: Boolean,
    var isLoggedIn: Boolean
)