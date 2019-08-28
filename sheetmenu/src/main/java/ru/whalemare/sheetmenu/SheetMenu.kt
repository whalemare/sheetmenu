package ru.whalemare.sheetmenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.MenuRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.whalemare.sheetmenu.adapter.MenuAdapter
import ru.whalemare.sheetmenu.extension.inflate
import ru.whalemare.sheetmenu.extension.marginTop
import ru.whalemare.sheetmenu.extension.toList
import ru.whalemare.sheetmenu.layout.LinearLayoutProvider

/**
 * Developed with ❤
 * @since 2017
 * @author Anton Vlasov - whalemare
 * @param title string with text for title
 * @param menu id from resources for auto-inflate menu
 * @param layoutManager for RecyclerView. By default: vertical linear layout manager. If you set this, @param <b>mode</b> is not be used.
 * @param click listener for menu items
 */
open class SheetMenu(
    private val title: String? = null,
    private val items: List<ActionItem> = emptyList(),
    private val onClick: ((ActionItem) -> Unit)? = null,
    private val onCancel: (() -> Unit)? = null,
    private val layoutProvider: LayoutProvider = LinearLayoutProvider(),
    private val showIcons: Boolean = true
) {
    var dialog: BottomSheetDialog? = null
    private var dialogLifecycleObserver: DialogLifecycleObserver? = null

    constructor(context: Context,
                @MenuRes menu: Int,
                title: String? = null,
                onClick: ((ActionItem) -> Unit)? = null,
                onCancel: (() -> Unit)? = null,
                layoutProvider: LayoutProvider = LinearLayoutProvider(),
                showIcons: Boolean = true
    ): this(title, context.inflate(menu).toList(), onClick, onCancel, layoutProvider, showIcons)

    fun show(context: Context) {
        if (context is LifecycleOwner) {
            show(context, context.lifecycle)
        }
    }

    fun show(context: Context, lifecycle: Lifecycle? = null) {
        val dialog = createDialog(context)
        lifecycle?.let { bindLifecycle(dialog, lifecycle) }
        dialog.show()
    }

    @SuppressLint("InflateParams")
    open fun createDialog(context: Context): BottomSheetDialog {
        val root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_horizontal_list, null)
        val textTitle: TextView = root.findViewById(R.id.text_title)
        val recycler: RecyclerView = root.findViewById(R.id.recycler_view)

        if (title.isNullOrBlank()) {
            textTitle.visibility = View.GONE
        } else {
            textTitle.text = title
            textTitle.visibility = View.VISIBLE
        }

        val dialog = BottomSheetDialog(context).apply {
            setContentView(root)
            if (onCancel != null) {
                setOnCancelListener { onCancel.invoke() }
            }
        }
        this.dialog = dialog

        recycler.layoutManager = layoutProvider.provideLayoutManager(context)
        recycler.adapter = MenuAdapter(items, {
            onClick?.invoke(it)
            dialog.dismiss()
        }, layoutProvider.provideItemLayoutRes(), showIcons)

        if (textTitle.visibility != View.VISIBLE && recycler.layoutManager is GridLayoutManager) {
            root.marginTop(24)
        }

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_HALF_EXPANDED
            behavior.peekHeight = -1
        }
        dialog.show()
        return dialog
    }

    protected open fun bindLifecycle(dialog: BottomSheetDialog, lifecycle: Lifecycle): DialogLifecycleObserver {
        dialogLifecycleObserver?.let {
            lifecycle.removeObserver(it)
        }
        dialogLifecycleObserver = DialogLifecycleObserver(dialog).also {
            lifecycle.addObserver(it)
        }
        return dialogLifecycleObserver!!
    }

}

