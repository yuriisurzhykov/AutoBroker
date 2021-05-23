package com.yuriysurzhikov.autobroker.ui.car

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentCarCreateBinding
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener
import com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe.IRefreshableFragment
import com.yuriysurzhikov.autobroker.ui.widget.sheets.BottomChooser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateCarFragment : AbstractFragment(), IRefreshableFragment {

    private lateinit var binding: FragmentCarCreateBinding
    private lateinit var viewModel: CreateCarViewModel
    private val carBrandAdapter = CarBrandPagerAdapter()
    private val carModelAdapter = CarModelRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, defaultViewModelProviderFactory).get(CreateCarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_car_create, container, false)
        binding = FragmentCarCreateBinding.bind(view)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.carBrandPager.adapter = carBrandAdapter
        binding.carBrandPager.registerOnPageChangeCallback(onCarBrandPageChanged)
        binding.carBrandPager.setOnClickListener(onBrandPageClicked)
        viewModel.observeCarBrands(viewLifecycleOwner, carBrandsObserver)
        viewModel.observeCarModels(viewLifecycleOwner, carModelsObserver)
        binding.actionSlideLeft.setOnClickListener {
            val current = binding.carBrandPager.currentItem
            if (current > 0) {
                binding.carBrandPager.setCurrentItem(current - 1, true)
            } else {
                binding.carBrandPager.setCurrentItem(carBrandAdapter.itemCount - 1, true)
            }
        }

        binding.actionSlideRight.setOnClickListener {
            val current = binding.carBrandPager.currentItem
            if (current + 1 < carBrandAdapter.itemCount) {
                binding.carBrandPager.setCurrentItem(current + 1, true)
            } else {
                binding.carBrandPager.setCurrentItem(0, true)
            }
        }
        binding.carModel.setOnClickListener {
            openCarModelDialog()
        }
    }

    private fun openCarModelDialog() {
        CoroutineScope(Dispatchers.IO).launch {
            val itemsMapped = carModelAdapter.getItems().map {
                CarModelBottomItem(it)
            }
            CoroutineScope(Dispatchers.Main).launch {
                val carModelBottomSheet =
                    BottomChooser.newInstance(
                        itemsMapped,
                        clickListener = object : OnItemClickListener<CarModelBottomItem> {
                            override fun onItemClick(item: CarModelBottomItem, position: Int) {
                                selectModelPosition(position)
                            }
                        },
                        itemRes = R.layout.list_item_car_model
                    )
                carModelBottomSheet.show(childFragmentManager, CAR_MODEL_DIALOG_TAG)
            }
        }
    }

    override fun refresh() {
        viewModel.invalidate()
    }

    private fun selectModelPosition(position: Int) {
        viewModel.selectCarModel(position)
        val brand = carBrandAdapter[viewModel.selectedCarBrand.get()]
        val model = brand?.models?.get(position)
        binding.carModel.text = model?.name
    }

    private val onCarBrandPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.selectedCarBrand.set(position)
        }
    }

    private val onBrandPageClicked = View.OnClickListener {
        Log.e(TAG, "onBrandPageClicked: carModelSize = ${carModelAdapter.itemCount}")
    }

    private val carBrandsObserver = Observer<List<CarBrand>?> {
        carBrandAdapter.setItems(it)
    }

    private val carModelsObserver = Observer<List<CarModel>?> {
        carModelAdapter.setItems(it)
    }

    companion object {

        private val TAG = CreateCarFragment::class.simpleName
        private val CAR_MODEL_DIALOG_TAG = "car_model_chooser"

        @JvmStatic
        fun newInstance() = CreateCarFragment()
    }
}