package com.yuriysurzhikov.autobroker.ui.home

import androidx.databinding.ObservableBoolean
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.yuriysurzhikov.autobroker.model.entity.Car
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.local.CarRoom
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
    private val mapper: IEntityMapper<CarBrand, CarWithModelsRoom>,
    private val carMapper: IEntityMapper<Car, CarRoom>
) : BaseViewModel() {

    val mutableList = MutableLiveData<List<Car>>()

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
            val userCars = userDatabase.getUserCarRepository().getAll().map {
                carMapper.mapToEntity(it)
            }
            mutableList.postValue(userCars)
            isEmpty.set(userCars.isEmpty())
            isLoading.set(false)
        }
    }
}