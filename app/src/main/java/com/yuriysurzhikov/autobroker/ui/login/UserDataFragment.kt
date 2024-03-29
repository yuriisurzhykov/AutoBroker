package com.yuriysurzhikov.autobroker.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentRegistrationUserDataBinding
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.ui.widget.adapters.RegionAdapter
import com.yuriysurzhikov.autobroker.util.isNotNullOrEmpty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDataFragment : AbstractLoginFragment() {

    private lateinit var binding: FragmentRegistrationUserDataBinding

    private val vocabViewModel: VocabularyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationUserDataBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.registerButton.setOnClickListener {
            attemptRegistration()
        }
        vocabViewModel.observeRegions(viewLifecycleOwner, regionsObserver)
        vocabViewModel.loadRegions()
        viewModel.observeRegistration(viewLifecycleOwner, registrationObserver)
    }

    private fun attemptRegistration() {
        if (checkAllField()) {
            val region = binding.regionAdapter?.getItem(viewModel.selectedRegionPosition.get())
            viewModel.attemptRegistration(region, binding.cityInput.text.toString())
        }
    }

    private fun checkAllField(): Boolean {
        val selectedPosition = viewModel.selectedRegionPosition.get()
        val isCityNotEmpty = binding.cityInput.toString().isNotNullOrEmpty()
        if ((selectedPosition != 0 && selectedPosition != Adapter.NO_SELECTION) && isCityNotEmpty) {
            return true
        }
        showMessage(context?.getString(R.string.error_required_fields_empty))
        return false
    }

    private val regionsObserver = Observer<List<Region>> {
        it?.let { _ ->
            val regionList = it.toMutableList()
            regionList.add(
                0,
                Region(
                    "",
                    listOf(StringItem("", requireContext().getString(R.string.label_select_region)))
                )
            )
            binding.regionAdapter = RegionAdapter(requireContext(), regionList.toTypedArray())
        }
    }

    private val registrationObserver = Observer<Int> {
        when (it) {
            ErrorCode.OK -> {
                val activity = activity
                if (activity is ILoginCallback) {
                    viewModel.user.get()?.strId?.let { it1 -> activity.onLoginSuccess(it1) }
                }
            }
            ErrorCode.ERROR_UNKNOWN -> {
            }
        }
    }

    companion object {
        fun newInstance() = UserDataFragment()
    }
}