package com.yuriysurzhikov.autobroker.ui.settings.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.FragmentUserFullDetailsBinding
import com.yuriysurzhikov.autobroker.model.entity.StringItem
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.repository.ErrorCode
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.list.StringItemAdapter
import com.yuriysurzhikov.autobroker.ui.settings.FragmentUserSettings
import com.yuriysurzhikov.autobroker.ui.settings.UserDetailsViewModel
import com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe.IStyleFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUserDetails : AbstractFragment(), IStyleFragment {

    private val viewModel: UserFieldsViewModel by viewModels()
    private val adapter = UserFieldsAdapter()
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
        binding.recycler.layoutManager = LinearLayoutManager(view.context)
        binding.recycler.adapter = adapter
        binding.viewModel = viewModel
        viewModel.setFieldsObserver(viewLifecycleOwner, detailsListObserver)
        viewModel.setUserObserver(viewLifecycleOwner, userObserver)
        viewModel.updateResult.observe(viewLifecycleOwner, Observer {
            if (it == ErrorCode.OK) {
                parentFragmentManager.popBackStack()
            }
        })
        binding.saveChangesBtn.setOnClickListener(onSaveClickListener)
    }

    private val userObserver = Observer<User?> {
        binding.user = it
    }

    private val detailsListObserver = Observer<List<IUserField>> {
        adapter.setItems(it)
    }

    private val onSaveClickListener = View.OnClickListener {
        val fields = adapter.getItems()
        viewModel.applyNewFields(fields)
    }

    companion object {
        fun newInstance() = FragmentUserDetails()
    }

    override fun getNavigationIcon() = null

    override fun getToolbarColor(): Int {
        return R.color.primary_color
    }

    override fun getTitleRes(): Int {
        return R.string.title_full_details
    }

    override fun getTitle(): String? {
        return context?.getString(R.string.title_full_details)
    }

    override fun getToolbar() = null
}