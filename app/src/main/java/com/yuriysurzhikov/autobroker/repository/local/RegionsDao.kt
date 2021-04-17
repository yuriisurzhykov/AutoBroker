package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.RegionRoom

@Dao
abstract class RegionsDao {

    @Delete
    abstract suspend fun delete(item: RegionRoom)

    @Update
    abstract suspend fun update(item: RegionRoom)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addAll(items: List<RegionRoom>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun add(item: RegionRoom)

    @Query("DELETE FROM region")
    abstract suspend fun clearRegions()

    @Query("SELECT * FROM region")
    abstract suspend fun getAll(): List<RegionRoom>
}