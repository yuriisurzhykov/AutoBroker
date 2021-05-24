package com.yuriysurzhikov.autobroker.repository.remote

import com.yuriysurzhikov.autobroker.model.entity.Car
import javax.inject.Inject

class RemoteRepository @Inject constructor() : IRemoteRepository {
    override suspend fun createCar(userId: String, car: Car) {

    }
}