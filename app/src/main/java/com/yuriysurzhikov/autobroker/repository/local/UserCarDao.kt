package com.yuriysurzhikov.autobroker.repository.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yuriysurzhikov.autobroker.model.local.CarRoom
import com.yuriysurzhikov.autobroker.repository.core.ICrudRepository

@Dao
interface UserCarDao : ICrudRepository<CarRoom> {
    @Query("DELETE FROM car")
    suspend fun clear()

    @Query("SELECT * FROM car")
    suspend fun getAll(): List<CarRoom>
}