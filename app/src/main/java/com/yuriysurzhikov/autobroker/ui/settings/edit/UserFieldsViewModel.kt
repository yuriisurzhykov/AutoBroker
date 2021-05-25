package com.yuriysurzhikov.autobroker.ui.settings.edit

import android.app.Application
import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import com.yuriysurzhikov.autobroker.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserFieldsViewModel
@ViewModelInject
constructor(
    private val userRepository: IUserLocalRepository,
    private val syncRepository: LocalSyncRepository,
    private val app: Application
) : AndroidViewModel(app) {

    private val userObservable = MutableLiveData<User?>()
    private val userFields = MutableLiveData<List<IUserField>>()
    val loading = ObservableBoolean(false)
    val updateResult = MutableLiveData<Int>()

    init {
        load()
    }

    fun setFieldsObserver(owner: LifecycleOwner, observer: Observer<List<IUserField>>) {
        userFields.observe(owner, observer)
    }

    fun setUserObserver(owner: LifecycleOwner, observer: Observer<User?>) {
        userObservable.observe(owner, observer)
    }

    fun load() {
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getMainUser()
            val detailsList = mutableListOf<IUserField>()
            if (user != null) {
                detailsList.add(
                    UserField(
                        UserField.FIELD_USER_NAME,
                        app.getString(R.string.hint_user_name),
                        user.displayName
                    )
                )
                detailsList.add(
                    UserField(
                        UserField.FIELD_CITY,
                        app.getString(R.string.hint_city),
                        user.location.city
                    )
                )
                detailsList.add(
                    UserField(
                        UserField.FIELD_REGION,
                        app.getString(R.string.hint_country),
                        user.location.regionName
                    )
                )
                detailsList.add(
                    UserField(
                        UserField.FIELD_EMAIL,
                        app.getString(R.string.hint_email_input),
                        user.email
                    )
                )
                detailsList.add(
                    UserField(
                        UserField.FIELD_PHONE,
                        app.getString(R.string.hint_phone),
                        user.phone
                    )
                )
                userFields.postValue(detailsList)
            }
            userObservable.postValue(user)
            loading.set(false)
        }
    }

    fun applyNewFields(fields: List<IUserField>) {
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val user = userRepository.getMainUser()
            if (user != null) {
                val displayName =
                    fields.find { it.getId() == UserField.FIELD_USER_NAME }?.getValue()
                if (displayName != user.displayName) {
                    user.displayName = displayName ?: user.displayName
                }
                val phone = fields.find { it.getId() == UserField.FIELD_PHONE }?.getValue()
                if (phone != user.phone) {
                    user.phone = phone
                }
                val email = fields.find { it.getId() == UserField.FIELD_EMAIL }?.getValue()
                if (email != user.email) {
                    user.email = email
                }
                val city = fields.find { it.getId() == UserField.FIELD_CITY }?.getValue()
                if (city != user.location.city) {
                    user.location.city = city ?: user.location.city
                }
                val region = fields.find { it.getId() == UserField.FIELD_REGION }?.getValue()
                if (region != user.location.regionName) {
                    user.location.regionName = region ?: user.location.regionName
                }
                userRepository.updateUser(user)
                updateResult.postValue(ErrorCode.OK)
            } else {
                updateResult.postValue(ErrorCode.ERROR_NO_SUCH_USER)
            }
            loading.set(false)
        }
    }

    class UserField
    constructor(private val id: String, private var hint: String?, private var value: String?) :
        IUserField {
        override fun getId() = id
        override fun getHint() = hint
        override fun getValue() = value
        override fun setValue(value: String?) {
            this.value = value
        }

        companion object {
            const val FIELD_USER_NAME = "user_name"
            const val FIELD_EMAIL = "email"
            const val FIELD_PHONE = "phone"
            const val FIELD_REGION = "region"
            const val FIELD_CITY = "city"
        }
    }
}