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

    @Query("SELECT * FROM usr WHERE strId=:id")
    fun getUserBy(id: String): UserRoom?
}