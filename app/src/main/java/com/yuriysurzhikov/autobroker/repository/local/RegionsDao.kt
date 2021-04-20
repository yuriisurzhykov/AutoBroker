package com.yuriysurzhikov.autobroker.repository.local

import androidx.room.*
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.repository.core.ICrudRepository

@Dao
abstract class RegionsDao :
    ICrudRepository<RegionRoom> {

    @Query("DELETE FROM region")
    abstract suspend fun clearRegions()

    @Query("SELECT * FROM region")
    abstract suspend fun getAll(): List<RegionRoom>
}