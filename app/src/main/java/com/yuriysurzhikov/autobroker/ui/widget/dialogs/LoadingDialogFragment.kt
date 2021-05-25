package com.yuriysurzhikov.autobroker.ui.widget.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.DialogFragment
import com.yuriysurzhikov.autobroker.R

class LoadingDialogFragment private constructor(val loading: ObservableBoolean) : DialogFragment() {

    private val callback = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            if (!loading.get() && dialog?.isShowing == true) {
                dismiss()
            }
        }
    }

    init {
        loading.addOnPropertyChangedCallback(callback)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context)
            .setView(R.layout.layout_loading_progress)
            .setCancelable(false)
            .create()
        builder.setCanceledOnTouchOutside(false)
        return builder
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        loading.removeOnPropertyChangedCallback(callback)
    }

    companion object {
        fun create(loading: ObservableBoolean) = LoadingDialogFragment(loading)
    }
}