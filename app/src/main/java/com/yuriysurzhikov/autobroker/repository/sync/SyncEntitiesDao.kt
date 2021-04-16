package com.yuriysurzhikov.autobroker.repository.sync

import androidx.room.Dao
import androidx.room.Query
import com.yuriysurzhikov.autobroker.model.local.RegionRoom
import com.yuriysurzhikov.autobroker.repository.ICrudRepository

@Dao
interface SyncEntitiesDao : ICrudRepository<RegionRoom> {

    @Query("DELETE FROM region")
    suspend fun clearRegions()

}