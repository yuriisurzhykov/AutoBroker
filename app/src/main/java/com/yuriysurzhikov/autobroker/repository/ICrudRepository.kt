package com.yuriysurzhikov.autobroker.repository

import androidx.room.*

@Dao
interface ICrudRepository<T> {
    @Delete
    suspend fun delete(item: T)

    @Update
    suspend fun update(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: T)
}