package com.yuriysurzhikov.autobroker.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriysurzhikov.autobroker.databinding.FragmentUserFullDetailsBinding
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.list.StringItemAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUserDetails : AbstractFragment() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private val adapter = StringItemAdapter()
    private lateinit var binding: FragmentUserFullDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserFullDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userDetails.actionsRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.userDetails.actionsRecycler.adapter = adapter
        viewModel.observeUserDetails(viewLifecycleOwner, userObserver)
        viewModel.observeListDetails(viewLifecycleOwner, detailsListObserver)
        viewModel.loadDetails()
        viewModel.buildUserDetailsList()
    }

    private val userObserver = Observer<User?> {
        binding.user = it
    }

    private val detailsListObserver = Observer<List<StringItem>> {
        adapter.setItems(it)
    }

    companion object {
        fun newInstance() = FragmentUserSettings()
    }
}