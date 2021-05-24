package com.yuriysurzhikov.autobroker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentHomeBinding
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.INavigationCallbacks
import com.yuriysurzhikov.autobroker.ui.car.CreateCarFragment
import com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe.IRefreshableFragment

class FragmentHome : AbstractFragment(), IRefreshableFragment {

    private val viewModel: FragmentHomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding
    private val adapter = CarBrandAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewModel = viewModel
        binding.recyclerAdapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(context)
        binding.addRecordAction.setOnClickListener(createCarClickListener)
        binding.createButton.setOnClickListener(createCarClickListener)
        viewModel.mutableList.observe(viewLifecycleOwner, Observer {
            adapter.setItems(it)
        })
    }

    companion object {
        fun newInstance() = FragmentHome()
    }

    override fun refresh() {
        viewModel.invalidate()
    }

    private val createCarClickListener = View.OnClickListener {
        val activity = activity
        if (activity is INavigationCallbacks) {
            val fragment = CreateCarFragment.newInstance()
            activity.showFragment(fragment)
        }
    }
}