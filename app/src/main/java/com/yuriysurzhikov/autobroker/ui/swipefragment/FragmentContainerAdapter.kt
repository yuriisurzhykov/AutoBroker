package com.yuriysurzhikov.autobroker.ui.swipefragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yuriysurzhikov.autobroker.ui.list.IMutableAdapter
import com.yuriysurzhikov.autobroker.ui.widget.fragmentswipe.FragmentContainer

class FragmentContainerAdapter : FragmentStateAdapter, IMutableAdapter<Fragment> {

    constructor(fragmentActivity: FragmentActivity) : super(fragmentActivity)
    constructor(fragment: Fragment) : super(fragment)
    constructor(fragmentManager: FragmentManager, lifecycle: Lifecycle) : super(
        fragmentManager,
        lifecycle
    )

    private val mItems = mutableListOf<FragmentContainer>()

    override fun getItemCount() = mItems.size

    override fun createFragment(position: Int) = mItems[position]

    override fun clear() {
        if (mItems.size > 0) {
            mItems.clear()
            notifyDataSetChanged()
        }
    }

    override fun getItems(): List<Fragment> {
        return ArrayList(mItems)
    }

    override fun setItems(list: List<Fragment>?) {
        clear()
        if (list != null) {
            list.forEachIndexed { index, fragment ->
                mItems.add(index, FragmentContainer.newInstance(fragment))
            }
            notifyDataSetChanged()
        }
    }

    override fun get(index: Int) = mItems[index]

    override fun updateItemAt(item: Fragment, position: Int) {
        if (position >= 0 && position < mItems.size) {
            mItems.removeAt(position)
            mItems.add(
                position, FragmentContainer.newInstance(item)
            )
            notifyDataSetChanged()
        }
    }
}