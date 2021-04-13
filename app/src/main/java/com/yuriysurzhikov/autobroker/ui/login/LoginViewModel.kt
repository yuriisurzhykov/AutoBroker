package com.yuriysurzhikov.autobroker.ui.login

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.google.firebase.auth.FirebaseUser
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.IUserRepository
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val localRepository: IUserRepository,
    private val entityMapper: IEntityMapper<FirebaseUser, User>
) : ViewModel() {

    private val userExists = MutableLiveData<Boolean>()
    private val loginCode = MutableLiveData<Int>()

    fun observeResult(owner: LifecycleOwner, observer: Observer<Int>) {
        loginCode.observe(owner, observer)
    }

    fun tryLogin(user: FirebaseUser?) {
        CoroutineScope(Dispatchers.IO).launch {
            user?.let {
                val isUserExists = localRepository.checkUserExists(user.uid)
                if (!isUserExists) {
                    localRepository.createUser(entityMapper.mapFromEntity(user))
                    loginCode.postValue(ErrorCode.ON_BOARDING_NEEDED)
                } else {
                    loginCode.postValue(ErrorCode.OK)
                }
            }
        }
    }
}