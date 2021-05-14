package com.yuriysurzhikov.autobroker.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.local.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.local.SyncDatabase
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentHomeViewModel
@ViewModelInject
constructor(
    private val localDatabase: SyncDatabase,
    private val mapper: IEntityMapper<CarBrand, CarWithModelsRoom>
) : ViewModel() {

    val mutableList = MutableLiveData<List<CarBrand>>()

    init {
        load()
    }

    fun invalidate() {
        load()
    }

    private fun load() {
        CoroutineScope(Dispatchers.IO).launch {
            val carWithModel = localDatabase.getCarsDao().getCarBrandWithModels()?.map { mapper.mapToEntity(it) }
            mutableList.postValue(carWithModel)
        }
    }
}