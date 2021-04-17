package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Dao
abstract class UserLocalDao {

    @Delete
    abstract suspend fun delete(item: UserRoom)

    @Update
    abstract suspend fun update(item: UserRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addAll(items: List<UserRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(item: UserRoom)

    @Query("SELECT * FROM usr WHERE userId=:id")
    abstract fun getUserBy(id: String?): UserRoom?

    @Query("SELECT * FROM USR LIMIT 1")
    abstract fun getFirstUser(): UserRoom?
}