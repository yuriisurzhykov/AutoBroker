package com.yuriysurzhikov.autobroker.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.ActivityMainBinding
import com.yuriysurzhikov.autobroker.model.events.LogoutEvent
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.INavigationCallbacks
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity
import com.yuriysurzhikov.autobroker.ui.menu.NavigationManager
import com.yuriysurzhikov.autobroker.ui.swipefragment.SwipeFragmentNavigation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity :
    AbstractActivity(),
    INavigationCallbacks,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = MainActivity::class.simpleName

    private val viewModel: MainActivityViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    private lateinit var swipeFragmentNavigation: SwipeFragmentNavigation

    private lateinit var navigationManager: NavigationManager

    override fun getLayoutRes() = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.bind(findViewById(R.id.activity_main))
        navigationManager = NavigationManager(this)
        swipeFragmentNavigation = SwipeFragmentNavigation.Builder(this)
            .withBottomNavigationView(binding!!.bottomMenu)
            .setOnNavigationListener(this)
            .withViewPager(binding!!.fragmentPager)
            .setOnPageChangeCallback(onPageChangeCallback)
            .withFragments(navigationManager.createPagerAdapter().getItems())
            .build()
        /*binding?.bottomMenu?.setOnNavigationItemSelectedListener(this)
        binding?.fragmentPager?.adapter = navigationManager.createPagerAdapter()
        binding?.fragmentPager?.registerOnPageChangeCallback(onPageChangeCallback)*/
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }.also {
            startActivity(it)
        }
    }

    override fun showFragment(fragment: Fragment, tag: String?) {
        swipeFragmentNavigation.showFragment(fragment, tag)
    }

    override fun openIntent(intent: Intent) {
        startActivity(intent)
    }

    override fun attemptLogout() {
        viewModel.logout()
    }

    override fun onBackPressed() {
        if (!swipeFragmentNavigation.onBackPressed())
            super.onBackPressed()
    }

    override fun onEmptyBackStack() {
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.fragmentPager?.currentItem = when (item.itemId) {
            R.id.home -> 0
            R.id.finder -> 1
            R.id.settings -> 2
            else -> 0
        }
        return true
    }

    @Subscribe(threadMode = ThreadMode.ASYNC, sticky = true, priority = 1)
    fun onLogout(event: LogoutEvent) {
        EventBus.getDefault().removeStickyEvent(event)
        signOut()
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            binding?.bottomMenu?.setOnNavigationItemSelectedListener(null)
            binding?.bottomMenu?.selectedItemId = when (position) {
                0 -> R.id.home
                1 -> R.id.finder
                2 -> R.id.settings
                else -> R.id.home
            }
            binding?.bottomMenu?.setOnNavigationItemSelectedListener(this@MainActivity)
        }
    }
}