package com.yuriysurzhikov.autobroker.ui.settings

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.Action
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.util.Const
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailsViewModel @ViewModelInject
constructor(
    private val app: Application,
    private val repository: IUserLocalRepository
) : AndroidViewModel(app) {

    private val userDetails = MutableLiveData<User?>()
    private val userDetailsList = MutableLiveData<List<StringItem>>()
    private val userActionsList = MutableLiveData<List<Action>>()

    fun observeUserDetails(owner: LifecycleOwner, observer: Observer<User?>) {
        userDetails.observe(owner, observer)
    }

    fun observeUserActions(
        owner: LifecycleOwner,
        observer: Observer<List<Action>>
    ) {
        userActionsList.observe(owner, observer)
    }

    fun observeListDetails(owner: LifecycleOwner, observer: Observer<List<StringItem>>) {
        userDetailsList.observe(owner, observer)
    }

    fun buildActions() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = app.applicationContext
            val result = mutableListOf<Action>()
            result.add(
                Action(
                    context.getString(R.string.label_full_info),
                    Const.GeneralConst.ACTION_FULL_INFO,
                    null,
                    R.drawable.ic_user
                )
            )
            result.add(
                Action(
                    context.getString(R.string.label_logout),
                    Const.GeneralConst.ACTION_LOGOUT,
                    null,
                    R.drawable.ic_log_out
                )
            )
            userActionsList.postValue(result)
        }
    }

    fun loadDetails() {
        CoroutineScope(Dispatchers.IO).launch {
            val user = repository.getMainUser()
            userDetails.postValue(user)
        }
    }

    fun buildUserDetailsList() {
        CoroutineScope(Dispatchers.IO).launch {
            val context = app.applicationContext
            val details = mutableListOf<StringItem>()
            val user = repository.getMainUser()
            details.add(StringItem(context.getString(R.string.label_region), user?.location?.regionName))
            details.add(StringItem(context.getString(R.string.label_city), user?.location?.city))
            details.add(StringItem(context.getString(R.string.label_email), user?.email))
            details.add(StringItem(context.getString(R.string.label_phone), user?.phone))
        }
    }

}