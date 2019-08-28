package ru.whalemare.sheetmenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.whalemare.sheetmenu.adapter.MenuAdapter
import ru.whalemare.sheetmenu.extension.marginTop
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
    val title: String? = null,
    val items: List<ActionItem> = emptyList(),
    val onClick: ((ActionItem) -> Unit)? = null,
    val onCancel: (() -> Unit)? = null,
    val layoutProvider: LayoutProvider = LinearLayoutProvider()
) {
    protected var dialog: BottomSheetDialog? = null
    protected var dialogLifecycleObserver: DialogLifecycleObserver? = null

    fun show(context: Context, lifecycle: Lifecycle) {
        dialogLifecycleObserver?.let {
            lifecycle.removeObserver(it)
        }
        val dialog = showDialog(context)
        dialogLifecycleObserver = DialogLifecycleObserver(dialog).also {
            lifecycle.addObserver(it)
        }
    }

    @Deprecated(
        message = "Use show(context: Context, lifecycle: Lifecycle)",
        replaceWith = ReplaceWith("show(context, lifecycle)")
    )
    fun show(context: Context) {
        showDialog(context)
    }

    @SuppressLint("InflateParams")
    open fun showDialog(context: Context): BottomSheetDialog {
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
//                setOnDismissListener{ onCancel?.invoke() }
                setOnCancelListener { onCancel.invoke() }
            }
        }
        this.dialog = dialog


        recycler.layoutManager = layoutProvider.provideLayoutManager(context)
        recycler.adapter = MenuAdapter(items, {
            onClick?.invoke(it)
            dialog.dismiss()
        }, layoutProvider.provideItemLayoutRes())

//        if (menu > 0) {
//            recycler.adapter = adapter
//                    ?: MenuAdapter(
//                        menuItems = recycler.context.inflate(menu).toList(),
//                        callback = MenuItem.OnMenuItemClickListener {
//                            click.onMenuItemClick(it)
//                            if (autoCancel) dialog.cancel()
//                            true
//                        },
//                        itemLayoutId = if (layoutManager is GridLayoutManager)
//                            R.layout.item_grid
//                        else
//                            R.layout.item_linear,
//                        showIcons = showIcons
//                    )
//                        .also { this.adapter = it }
//
//            recycler.layoutManager = layoutManager
//                    ?: LinearLayoutManager(recycler.context, RecyclerView.VERTICAL, false)
//                        .also { this.layoutManager = it }
//        }


        if (textTitle.visibility == View.VISIBLE && recycler.layoutManager is GridLayoutManager) {
            root.marginTop(24)
        }

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 0
        }
        dialog.show()
        return dialog
    }

    open fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }

    open fun cancel() {
        dialog?.cancel()
        dialog = null
    }

    protected open fun processClick(
        dialog: BottomSheetDialog,
        click: MenuItem.OnMenuItemClickListener,
        autoCancel: Boolean
    ): MenuItem.OnMenuItemClickListener {
        return if (autoCancel) {
            MenuItem.OnMenuItemClickListener {
                click.onMenuItemClick(it)
                dialog.cancel()
                true
            }
        } else {
            click
        }
    }

    fun apply(action: ActionSingle<SheetMenu>): SheetMenu {
        action.call(this)
        return this
    }

    interface ActionSingle<in T> {
        fun call(it: T)
    }
    //endregion
}

