package com.yuriysurzhikov.autobroker.messaging

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yuriysurzhikov.autobroker.AutoBrokerApplication
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.repository.core.IUserLocalRepository
import com.yuriysurzhikov.autobroker.repository.works.WorkExecutorManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class AppMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var repository: IUserLocalRepository

    @Inject
    lateinit var syncronizer: ISynchronizer

    override fun onNewToken(newToken: String) {
        super.onNewToken(newToken)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val data = message.data[USER_ID_ARG]
        message.notification?.let {

        }
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (data == repository.getMainUser()?.strId) {
                    proceedData(message.data[DATA_TYPE_ARG])
                } else {
                    processResultInBase(message)
                }
            } catch (e: Throwable) {
                processResultInBase(message)
            }
        }
    }

    private suspend fun processResultInBase(message: RemoteMessage) =
        withContext(Dispatchers.Main) {
            super.onMessageReceived(message)
        }

    private suspend fun proceedData(dataType: String?) = withContext(Dispatchers.Main) {
        when (dataType) {
            DATA_TYPE_CAR -> {
                WorkExecutorManager.executeSyncUsersCars(applicationContext)
            }
            DATA_TYPE_USER -> {
                WorkExecutorManager.executeSyncUserData(applicationContext)
            }
            else -> {
                if (application is AutoBrokerApplication) {
                    (application as AutoBrokerApplication).sync()
                }
            }
        }
    }

    companion object {
        private const val USER_ID_ARG = "user_id"
        private const val DATA_TYPE_ARG = "data_type"
        private const val DATA_TYPE_CAR = "car_data_type"
        private const val DATA_TYPE_USER = "user_data_type"
    }
}