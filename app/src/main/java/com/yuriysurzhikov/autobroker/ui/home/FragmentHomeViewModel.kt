package com.yuriysurzhikov.autobroker.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.database.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.ui.core.BaseViewModel
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FragmentHomeViewModel
@ViewModelInject
constructor(
    private val localDatabase: SyncDatabase,
    private val userDatabase: LocalDatabase,
    private val mapper: IEntityMapper<CarBrand, CarWithModelsRoom>
) : BaseViewModel() {

    val mutableList = MutableLiveData<List<CarBrand>>()

    init {
        load()
    }

    fun invalidate() {
        load()
    }

    private fun load() {
        if (isLoading.get()) {
            return
        }
        isLoading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val carWithModel = localDatabase.getCarsDao().getCarBrandWithModels()?.map { mapper.mapToEntity(it) }
            mutableList.postValue(carWithModel)
            isEmpty.set(carWithModel.isNullOrEmpty())
            isLoading.set(false)
        }
    }
}