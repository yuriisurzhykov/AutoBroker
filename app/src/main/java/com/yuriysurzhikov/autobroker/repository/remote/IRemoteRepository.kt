package com.yuriysurzhikov.autobroker.repository.remote

import com.yuriysurzhikov.autobroker.model.entity.Car

interface IRemoteRepository {
    suspend fun createCar(userId: String, car: Car)
}