package com.yuriysurzhikov.autobroker.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentRegistrationUserDataBinding
import com.yuriysurzhikov.autobroker.model.entity.Region
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.ui.adapter.RegionAdapter
import com.yuriysurzhikov.autobroker.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserDataFragment : AbstractLoginFragment() {

    private lateinit var binding: FragmentRegistrationUserDataBinding

    private val vocabViewModel: VocabularyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        val region = binding.regionAdapter?.getItem(viewModel.selectedRegionPosition.get())
        viewModel.attemptRegistration(region, binding.cityInput.toString())
    }

    private fun checkAllField(): Boolean {
        val selectedPosition = viewModel.selectedRegionPosition.get()
        val isCityNotEmpty = binding.cityInput.toString().isNotEmpty()
        if (selectedPosition != 0 && isCityNotEmpty) {
            return true
        }
        return false
    }

    private val regionsObserver = Observer<List<Region>> {
        it?.let { regions ->
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
                Intent(activity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }.also {
                    startActivity(it)
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