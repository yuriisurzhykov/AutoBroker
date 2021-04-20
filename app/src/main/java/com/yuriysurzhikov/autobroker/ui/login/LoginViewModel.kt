package com.yuriysurzhikov.autobroker.ui.login

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.model.entity.UserLocation
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Error

class LoginViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val localLocalRepository: IUserLocalRepository,
    private val entityMapper: IEntityMapper<FirebaseUser, User>
) : ViewModel() {

    private val userExists = MutableLiveData<Boolean>()
    private val loginCode = MutableLiveData<Pair<Int, Boolean>>()
    private val registrationCode = MutableLiveData<Int>()

    val userCity = ObservableField<String>()
    val loading = ObservableBoolean(false)
    val selectedRegionPosition = ObservableInt()

    fun observeResult(owner: LifecycleOwner, observer: Observer<Pair<Int, Boolean>>) {
        loginCode.observe(owner, observer)
    }

    fun observeRegistration(owner: LifecycleOwner, observer: Observer<Int>) {
        registrationCode.observe(owner, observer)
    }

    fun tryLogin(user: FirebaseUser?) {
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                user?.let {
                    val isUserExists = localLocalRepository.checkUserExists(user.uid)
                    if (!isUserExists) {
                        localLocalRepository.createUser(entityMapper.mapFromEntity(user))
                        loginCode.postValue(Pair(ErrorCode.ERROR_ON_BOARDING_NEEDED, true))
                    } else {
                        val userById = localLocalRepository.getUser(user.uid)
                        if (userById != null) {
                            if (userById.fullRegistration) {
                                localLocalRepository.login(userById)
                                loginCode.postValue(Pair(ErrorCode.OK, true))
                            } else {
                                loginCode.postValue(Pair(ErrorCode.ERROR_ON_BOARDING_NEEDED, true))
                            }
                        } else {
                            loginCode.postValue(Pair(ErrorCode.ERROR_FAILED_LOGIN, false))
                        }
                    }
                }
            } finally {
                loading.set(false)
            }
        }
    }

    fun tryLogin(id: String?, password: String, attemptByUser: Boolean) {
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val userById = localLocalRepository.getUserByLogin(id)
                if (userById != null) {
                    if (!userById.fullRegistration) {
                        loginCode.postValue(Pair(ErrorCode.ERROR_ON_BOARDING_NEEDED, attemptByUser))
                    } else {
                        localLocalRepository.login(userById)
                        loginCode.postValue(Pair(ErrorCode.OK, attemptByUser))
                    }
                } else {
                    loginCode.postValue(Pair(ErrorCode.ERROR_NO_SUCH_USER, attemptByUser))
                }
            } finally {
                loading.set(false)
            }
        }
    }

    fun attemptRegistration(region: Region?, city: String?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (region != null && city != null) {
                try {
                    val user = localLocalRepository.getMainUser()
                    user?.location = UserLocation(city, region.externalId)
                    user?.isLoggedIn = true
                    user?.fullRegistration = true
                    localLocalRepository.register(user!!)
                    registrationCode.postValue(ErrorCode.OK)
                } catch (e: Throwable) {
                    registrationCode.postValue(ErrorCode.ERROR_UNKNOWN)
                }
            }
        }
    }
}