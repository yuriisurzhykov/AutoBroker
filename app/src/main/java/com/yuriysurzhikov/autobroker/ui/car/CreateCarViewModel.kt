package com.yuriysurzhikov.autobroker.ui.car

import android.app.Application
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.WorkerThread
import androidx.core.net.toFile
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.model.entity.RegionNumber
import com.yuriysurzhikov.autobroker.model.local.CarWithModelsRoom
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.repository.database.LocalDatabase
import com.yuriysurzhikov.autobroker.repository.database.SyncDatabase
import com.yuriysurzhikov.autobroker.repository.local.UserRepositoryImpl
import com.yuriysurzhikov.autobroker.repository.utils.RegionNumberConverter
import com.yuriysurzhikov.autobroker.ui.widget.adapters.UserAttachesAdapter
import com.yuriysurzhikov.autobroker.util.CarNumberUtils
import com.yuriysurzhikov.autobroker.util.IEntityMapper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCarViewModel
@ViewModelInject
constructor(
    private val app: Application,
    private val userLocalRepository: UserRepositoryImpl,
    private val localDatabase: LocalDatabase,
    private val syncDatabase: SyncDatabase,
    private val carBrandLocalMapper: IEntityMapper<CarBrand, CarWithModelsRoom>
) : AndroidViewModel(app) {

    private val TAG = CreateCarViewModel::class.simpleName

    private val carBrands = MutableLiveData<List<CarBrand>?>()
    private val carModelsByBrand = MutableLiveData<List<CarModel>?>()

    private val selectedImagesUri = MutableLiveData<List<Uri>>(emptyList())

    private val createResult = MutableLiveData<Int>()

    val loading = ObservableBoolean(false)

    val price = ObservableField<String>()
    val adTitle = ObservableField<String>()
    val mileage = ObservableField<String>()
    val selectedModelName = ObservableField<String?>()
    val selectedCarModel = ObservableField<CarModel>()
    val regionCode = ObservableField<String>()
    val number = ObservableField<String>()
    val serialNumber = ObservableField<String>()

    val regionCodeError = ObservableField<String?>()
    val serialCodeError = ObservableField<String?>()
    val numberError = ObservableField<String?>()
    val adTitleError = ObservableField<String>()
    val priceError = ObservableField<String>()
    val mileageError = ObservableField<String>()

    val selectedCarBrand = ObservableInt()
    val userAttachesEmpty = ObservableBoolean(true)

    val userAttachesLiveList: LiveData<List<Uri>>
        get() = selectedImagesUri

    private val onBrandReselected = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            CoroutineScope(Dispatchers.IO).launch {
                loadCarModels()
            }
        }
    }

    init {
        selectedCarBrand.addOnPropertyChangedCallback(onBrandReselected)
        regionCode.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.e(TAG, "onPropertyChanged: ${regionCode.get()}")
            }
        })
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

    fun observeAttaches(
        owner: LifecycleOwner,
        observer: Observer<List<UserAttachesAdapter.UserAttach>>
    ) {
        selectedImagesUri.observe(owner, Observer { uriList ->
            observer.onChanged(uriList?.map { UserAttachesAdapter.UserAttach(it) })
        })
    }

    fun selectCarModel(position: Int) {
        val selectedBrand = carBrands.value?.get(selectedCarBrand.get())
        val newModel = selectedBrand?.models?.get(position)
        selectedCarModel.set(newModel)
        selectedModelName.set(newModel?.name)
    }

    fun selectCarBrandPage(position: Int) {
        selectedModelName.set(null)
        selectedCarModel.set(null)
        selectedCarBrand.set(position)
    }

    fun addAttach(uri: Uri) {
        val selectedImages = selectedImagesUri.value.orEmpty().toMutableList()
        selectedImages.add(uri)
        selectedImagesUri.postValue(selectedImages)
        userAttachesEmpty.set(selectedImages.isEmpty())
    }

    fun removeAttach(position: Int) {
        val selected = selectedImagesUri.value.orEmpty().toMutableList()
        selected.removeAt(position)
        selectedImagesUri.postValue(selected)
        userAttachesEmpty.set(selected.isEmpty())
    }

    fun attemptCreate(view: View) {
        if (!validateFields()) {
            return
        }
        loading.set(true)
        CoroutineScope(Dispatchers.IO).launch {
            val user = userLocalRepository.getMainUser()
            if (user != null) {
                try {
                    val carModel = selectedCarModel.get()
                    val price = price.get() ?: ""
                    val title = adTitle.get() ?: ""
                    val mileage = mileage.get() ?: ""
                    val carBrand = carBrands.value?.get(selectedCarBrand.get())
                    val carNumber =
                        RegionNumberConverter().convertFromString("${regionCode.get()}|${number.get()}|${serialNumber.get()}")
                    userLocalRepository.createCar(
                        price,
                        title,
                        mileage,
                        carModel!!,
                        carBrand!!,
                        carNumber,
                        selectedImagesUri.value!!
                    )
                } finally {
                    loading.set(false)
                }
            } else {
                loading.set(false)
                createResult.postValue(ErrorCode.LOGIN_NEEDED)
            }
        }
    }

    private fun validateFields(): Boolean {
        var isValid = true
        if (regionCode.get().isNullOrEmpty() || !CarNumberUtils.isValidRegion(regionCode.get())) {
            regionCodeError.set(app.getString(R.string.error_region_code_invalid))
            isValid = false
        }
        if (number.get().isNullOrEmpty() || number.get()!!.length < 4) {
            numberError.set(app.getString(R.string.error_number_invalid))
            isValid = false
        }
        if (adTitle.get().isNullOrEmpty()) {
            adTitleError.set(app.getString(R.string.error_required_fields_empty))
            isValid = false
        }
        if (price.get().isNullOrEmpty()) {
            priceError.set(app.getString(R.string.error_required_fields_empty))
            isValid = false
        }
        if (mileage.get().isNullOrEmpty()) {
            mileageError.set(app.getString(R.string.error_required_fields_empty))
            isValid = false
        }
        if (selectedCarModel.get() == null) {
            Toast.makeText(app, app.getString(R.string.msg_select_model), Toast.LENGTH_LONG).show()
        }
        return isValid
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
        val brandsMapped = carBrandLocalMapper.mapListToEntity(brandsRoom).sortedBy {
            it.name
        }
        carBrands.postValue(brandsMapped)
    }

    @WorkerThread
    private suspend fun loadCarModels() {
        val brandsRoom = syncDatabase.getCarsDao().getCarBrandWithModels()
        val brandsMapped = carBrandLocalMapper.mapListToEntity(brandsRoom)
        carModelsByBrand.postValue(brandsMapped[selectedCarBrand.get()].models.sortedBy {
            it.name
        })
    }
}