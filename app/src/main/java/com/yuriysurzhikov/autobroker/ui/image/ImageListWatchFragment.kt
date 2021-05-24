package com.yuriysurzhikov.autobroker.ui.image

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.yuriysurzhikov.autobroker.databinding.FragmentImageWatchBinding
import com.yuriysurzhikov.autobroker.ui.AbstractFragment

class ImageListWatchFragment : AbstractFragment() {

    private lateinit var binding: FragmentImageWatchBinding

    private val adapter = ImageListAdapter()
    private val mItems = mutableListOf<Uri>()
    private var selectedPosition = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentImageWatchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imagePager.adapter = adapter
        adapter.setItems(mItems)
        binding.actionSlideLeft.setOnClickListener {
            val current = binding.imagePager.currentItem
            if (current > 0) {
                binding.imagePager.setCurrentItem(current - 1, true)
            } else {
                binding.imagePager.setCurrentItem(adapter.itemCount - 1, true)
            }
        }

        binding.actionSlideRight.setOnClickListener {
            val current = binding.imagePager.currentItem
            if (current + 1 < adapter.itemCount) {
                binding.imagePager.setCurrentItem(current + 1, true)
            } else {
                binding.imagePager.setCurrentItem(0, true)
            }
        }
        binding.imagePager.setCurrentItem(selectedPosition, true)
        binding.imagePager.setOnTouchListener { v, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    binding.imagePager.requestDisallowInterceptTouchEvent(true)
                }
                MotionEvent.ACTION_UP -> {
                    binding.imagePager.requestDisallowInterceptTouchEvent(false)
                }
            }
            return@setOnTouchListener true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(list: List<Uri>, selectedPosition: Int = 0) =
            ImageListWatchFragment().apply {
                mItems.clear()
                mItems.addAll(list)
                this.selectedPosition = selectedPosition
            }
    }
}