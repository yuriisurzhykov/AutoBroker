package com.yuriysurzhikov.autobroker.ui.settings.edit

interface IUserField {
    fun getId(): String?
    fun getHint(): String?
    fun getValue(): String?
    fun setValue(value: String?)
}