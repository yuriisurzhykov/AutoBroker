package com.yuriysurzhikov.autobroker.repository.local

import com.yuriysurzhikov.autobroker.repository.ISyncRepository
import javax.inject.Inject

class SyncLocalRepository @Inject constructor(
    syncEntitiesDao: SyncEntitiesDao
) : ISyncRepository {

}