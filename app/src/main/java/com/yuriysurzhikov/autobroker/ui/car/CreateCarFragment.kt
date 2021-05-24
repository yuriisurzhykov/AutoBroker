package com.yuriysurzhikov.autobroker.ui.car

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentCarCreateBinding
import com.yuriysurzhikov.autobroker.model.entity.CarBrand
import com.yuriysurzhikov.autobroker.model.entity.CarModel
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.INavigationCallbacks
import com.yuriysurzhikov.autobroker.ui.image.ImageListWatchFragment
import com.yuriysurzhikov.autobroker.ui.list.OnItemClickListener
import com.yuriysurzhikov.autobroker.ui.widget.adapters.UserAttachesAdapter
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
    private val userAttachesAdapter = UserAttachesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            defaultViewModelProviderFactory
        ).get(CreateCarViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_car_create, container, false)
        binding = FragmentCarCreateBinding.bind(view)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.carBrandPager.adapter = carBrandAdapter
        binding.carBrandPager.registerOnPageChangeCallback(onCarBrandPageChanged)
        binding.carBrandPager.setOnClickListener(onBrandPageClicked)
        userAttachesAdapter.onAddClickListener = onAddAttachClick
        userAttachesAdapter.onItemClickListener = onOpenImageFullClick
        userAttachesAdapter.onDeleteClickListener = onRemoveAttachClick
        binding.userAttachesPager.adapter = userAttachesAdapter
        binding.userAttachesPager.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.addMoreImages.setOnClickListener(onAddAttachClick)
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
        binding.userAttachesPager.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    binding.userAttachesPager.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> {
                    binding.userAttachesPager.requestDisallowInterceptTouchEvent(false)
                }
            }
            return@setOnTouchListener false
        }
        binding.carBrandPager.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    binding.carBrandPager.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> {
                    binding.carBrandPager.requestDisallowInterceptTouchEvent(false)
                }
            }
            return@setOnTouchListener false
        }
        viewModel.observeCarBrands(viewLifecycleOwner, carBrandsObserver)
        viewModel.observeCarModels(viewLifecycleOwner, carModelsObserver)
        viewModel.observeAttaches(viewLifecycleOwner, attachesObserver)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQUEST_IMAGE_PICK -> processImagePick(resultCode, data)
        }
    }

    private fun selectModelPosition(position: Int) {
        viewModel.selectCarModel(position)
        val brand = carBrandAdapter[viewModel.selectedCarBrand.get()]
        val model = brand?.models?.get(position)
        binding.carModel.text = model?.name
    }

    private fun chooseFromGallery() {
        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI).apply {
            type = "image/*"
        }.run {
            startActivityForResult(
                Intent.createChooser(
                    this,
                    getString(R.string.label_action_pick_image)
                ), REQUEST_IMAGE_PICK
            )
        }
    }

    private fun processImagePick(resultCode: Int, data: Intent?) {
        data?.data?.let { viewModel.addAttach(it) }
    }

    private val onBrandPageClicked = View.OnClickListener {
        openCarModelDialog()
    }

    private val onAddAttachClick = View.OnClickListener {
        chooseFromGallery()
    }

    private val onOpenImageFullClick =
        object : OnItemClickListener<UserAttachesAdapter.UserAttach> {
            override fun onItemClick(item: UserAttachesAdapter.UserAttach, position: Int) {
                openImageFullFragment(position)
            }
        }

    private val onRemoveAttachClick = object : OnItemClickListener<UserAttachesAdapter.UserAttach> {
        override fun onItemClick(item: UserAttachesAdapter.UserAttach, position: Int) {
            viewModel.removeAttach(position)
        }
    }

    private fun openImageFullFragment(position: Int) {
        val activity = activity
        if (activity is INavigationCallbacks) {
            val fragment =
                ImageListWatchFragment.newInstance(
                    viewModel.userAttachesLiveList.value.orEmpty(),
                    position
                )
            (activity as INavigationCallbacks).showFragment(fragment)
        }
    }

    private val onCarBrandPageChanged = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            viewModel.selectCarBrandPage(position)
        }
    }

    private val carBrandsObserver = Observer<List<CarBrand>?> {
        carBrandAdapter.setItems(it)
    }

    private val carModelsObserver = Observer<List<CarModel>?> {
        carModelAdapter.setItems(it)
    }

    private val attachesObserver = Observer<List<UserAttachesAdapter.UserAttach>> {
        userAttachesAdapter.setItems(it)
    }

    companion object {

        private val TAG = CreateCarFragment::class.simpleName
        private val CAR_MODEL_DIALOG_TAG = "car_model_chooser"

        private const val REQUEST_IMAGE_PICK = 101
        private const val REQUEST_IMAGE_CAPTURE = 102

        @JvmStatic
        fun newInstance() = CreateCarFragment()
    }
}