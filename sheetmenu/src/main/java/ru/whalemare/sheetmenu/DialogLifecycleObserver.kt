package ru.whalemare.sheetmenu

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.bottomsheet.BottomSheetDialog

class DialogLifecycleObserver(
    private val dialog: BottomSheetDialog
) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun dismiss() = dialog.dismiss()
}