package com.yuriysurzhikov.autobroker.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

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