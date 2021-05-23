package com.yuriysurzhikov.autobroker.ui.car

import androidx.annotation.WorkerThread
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.database.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCarViewModel
@ViewModelInject
constructor(
    private val localDatabase: LocalDatabase,
    private val syncDatabase: SyncDatabase,
    private val carBrandLocalMapper: IEntityMapper<CarBrand, CarWithModelsRoom>
) : ViewModel() {

    private val carBrands = MutableLiveData<List<CarBrand>?>()
    private val carModelsByBrand = MutableLiveData<List<CarModel>?>()

    val loading = ObservableBoolean(false)

    val price = ObservableField<String>()
    val adTitle = ObservableField<String>()
    val mileage = ObservableField<String>()
    val selectedModelName = ObservableField<String>()
    val selectedCarModel = ObservableField<CarModel>()

    val selectedCarBrand = ObservableInt()

    private val onBrandReselected = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                loadCarModels()
            }
        }
    }

    init {
        selectedCarBrand.addOnPropertyChangedCallback(onBrandReselected)
        load()
    }

    fun invalidate() {
        load()
    }

    fun observeCarBrands(owner: LifecycleOwner, observer: Observer<List<CarBrand>?>) {
        carBrands.observe(owner, observer)
    }

    fun observeCarModels(owner: LifecycleOwner, observer: Observer<List<CarModel>?>) {
        carModelsByBrand.observe(owner, observer)
    }

    fun selectCarModel(position: Int) {
        val selectedBrand = carBrands.value?.get(selectedCarBrand.get())
        val newModel = selectedBrand?.models?.get(position)
        selectedCarModel.set(newModel)
        selectedModelName.set(newModel?.name)
    }

    private fun load() {
        if (loading.get()) {
            return
        }
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            loadCarBrands()
            loadCarModels()
            loading.set(false)
        }
    }

    @WorkerThread
    private suspend fun loadCarBrands() {
        val brandsRoom = syncDatabase.getCarsDao().getCarBrandWithModels()
        val brandsMapped = carBrandLocalMapper.mapListToEntity(brandsRoom)
        carBrands.postValue(brandsMapped)
    }

    @WorkerThread
    private suspend fun loadCarModels() {
        val brandsRoom = syncDatabase.getCarsDao().getCarBrandWithModels()
        val brandsMapped = carBrandLocalMapper.mapListToEntity(brandsRoom)
        carModelsByBrand.postValue(brandsMapped[selectedCarBrand.get()].models)
    }
}