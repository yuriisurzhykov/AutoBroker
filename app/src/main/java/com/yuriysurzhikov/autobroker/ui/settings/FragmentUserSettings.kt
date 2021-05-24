package com.yuriysurzhikov.autobroker.ui.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuriysurzhikov.autobroker.databinding.FragmentUserDetailsBinding
import com.yuriysurzhikov.autobroker.model.entity.Action
import com.yuriysurzhikov.autobroker.model.entity.User
import com.yuriysurzhikov.autobroker.ui.AbstractFragment
import com.yuriysurzhikov.autobroker.ui.navigator.ActionNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentUserSettings : AbstractFragment() {

    private val viewModel: UserDetailsViewModel by viewModels()
    private lateinit var binding: FragmentUserDetailsBinding
    private var actionNavigator: ActionNavigator? = null
    private val adapter = UserActionsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        actionNavigator = ActionNavigator()
        actionNavigator?.attach(context, this)
    }

    override fun onDetach() {
        super.onDetach()
        actionNavigator?.detach()
        actionNavigator = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.userDetails.actionsRecycler.layoutManager = LinearLayoutManager(view.context)
        binding.userDetails.actionsRecycler.adapter = adapter
        viewModel.observeUserDetails(viewLifecycleOwner, userObserver)
        viewModel.observeUserActions(viewLifecycleOwner, actionsObserver)
        adapter.setOnActionClickListener(actionNavigator)
        viewModel.loadDetails()
        viewModel.buildActions()
    }

    private val userObserver = Observer<User?> {
        binding.user = it
    }

    private val actionsObserver = Observer<List<Action>> {
        adapter.setItems(it)
    }

    companion object {
        fun newInstance() = FragmentUserSettings()
    }
}
