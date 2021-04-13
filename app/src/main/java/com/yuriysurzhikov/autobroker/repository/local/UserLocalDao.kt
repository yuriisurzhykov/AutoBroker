package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.UserRoom

@Dao
interface UserLocalDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(userRoom: UserRoom)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userRoom: UserRoom)

    @Delete
    fun delete(userRoom: UserRoom)

    @Query("SELECT * FROM usr WHERE userId=:id")
    fun getUserBy(id: String?): UserRoom?

    @Query("SELECT * FROM USR LIMIT 1")
    fun getFirstUser(): UserRoom?
}