package com.yuriysurzhikov.autobroker.repository.core

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface ICrudRepository<T> {
    @Delete
    fun delete(item: T)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun update(item: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(items: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(item: T)
}