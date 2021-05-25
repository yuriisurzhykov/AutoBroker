package com.yuriysurzhikov.autobroker.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MenuItem
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.yuriysurzhikov.autobroker.AutoBrokerApplication
import com.yuriysurzhikov.autobroker.R
import com.yuriysurzhikov.autobroker.databinding.ActivityMainBinding
import com.yuriysurzhikov.autobroker.model.events.LogoutEvent
import com.yuriysurzhikov.autobroker.model.events.SyncFailedEvent
import com.yuriysurzhikov.autobroker.model.events.SyncSuccessEvent
import com.yuriysurzhikov.autobroker.repository.core.ISynchronizer
import com.yuriysurzhikov.autobroker.ui.AbstractActivity
import com.yuriysurzhikov.autobroker.ui.INavigationCallbacks
import com.yuriysurzhikov.autobroker.ui.login.LoginActivity
import com.yuriysurzhikov.autobroker.ui.menu.NavigationManager
import com.yuriysurzhikov.autobroker.ui.swipefragment.SwipeFragmentNavigation
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class MainActivity :
    AbstractActivity(),
    INavigationCallbacks,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = MainActivity::class.simpleName

    @Inject
    lateinit var syncronizer: ISynchronizer

    private val viewModel: MainActivityViewModel by viewModels()
    private var binding: ActivityMainBinding? = null
    private lateinit var swipeFragmentNavigation: SwipeFragmentNavigation

    private lateinit var navigationManager: NavigationManager

    private lateinit var gestureDetector: GestureDetector

    private val tapListener: GestureDetector.OnDoubleTapListener =
        object : GestureDetector.OnDoubleTapListener {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                return false
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                AutoBrokerApplication.sync(syncronizer)
                return true
            }

            override fun onDoubleTapEvent(e: MotionEvent): Boolean {
                AutoBrokerApplication.sync(syncronizer)
                return true
            }
        }

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
        gestureDetector = GestureDetector(this, SimpleOnGestureListener())
        gestureDetector.setOnDoubleTapListener(tapListener)
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

    override fun backToMain() {
        swipeFragmentNavigation.backToMain()
        binding?.bottomMenu?.selectedItemId = R.id.home
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

    override fun onSyncSuccess(event: SyncSuccessEvent) {
        super.onSyncSuccess(event)
        swipeFragmentNavigation.refresh()
    }

    override fun onSyncFailed(event: SyncFailedEvent) {
        super.onSyncFailed(event)
        swipeFragmentNavigation.refresh()
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