package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.UserRoom
import com.yuriysurzhikov.autobroker.repository.ICrudRepository

@Dao
interface UserLocalDao : ICrudRepository<UserRoom> {

    @Query("SELECT * FROM usr WHERE userId=:id")
    fun getUserBy(id: String?): UserRoom?

    @Query("SELECT * FROM USR LIMIT 1")
    fun getFirstUser(): UserRoom?
}