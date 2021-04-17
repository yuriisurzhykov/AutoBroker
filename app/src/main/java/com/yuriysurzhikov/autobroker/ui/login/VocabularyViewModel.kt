package com.yuriysurzhikov.autobroker.ui.login

import android.app.Application
import androidx.databinding.ObservableInt
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.FuelType
import com.yuriysurzhikov.autobroker.model.entity.GearboxType
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.repository.sync.LocalSyncRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocabularyViewModel @ViewModelInject constructor(
    private val app: Application,
    private val syncRepository: LocalSyncRepository
) : AndroidViewModel(app) {

    private val regionsList = MutableLiveData<List<Region>>()
    private val fuelTypesList = MutableLiveData<List<FuelType>>()
    private val gearBoxTypeList = MutableLiveData<List<GearboxType>>()

    fun observeRegions(owner: LifecycleOwner, observer: Observer<List<Region>>) {
        regionsList.observe(owner, observer)
    }

    fun loadRegions() {
        CoroutineScope(Dispatchers.IO).launch {
            val regions = syncRepository.fetchRegions().toMutableList()
            regionsList.postValue(regions)
        }
    }

    fun loadFuelTypes() {
        CoroutineScope(Dispatchers.IO).launch {
            val regions = syncRepository.fetchFuelTypes().toMutableList()
            fuelTypesList.postValue(regions)
        }
    }
}