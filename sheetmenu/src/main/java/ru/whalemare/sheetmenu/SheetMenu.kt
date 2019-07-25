package ru.whalemare.sheetmenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import ru.whalemare.sheetmenu.adapter.MenuAdapter
import ru.whalemare.sheetmenu.extension.inflate
import ru.whalemare.sheetmenu.extension.marginTop
import ru.whalemare.sheetmenu.extension.toList

/**
 * Developed with ❤
 * @since 2017
 * @author Anton Vlasov - whalemare
 * @param titleId id from resources with text for title. it is more important than common <b>title</b> param
 * @param title string with text for title
 * @param menu id from resources for auto-inflate menu
 * @param layoutManager for RecyclerView. By default: vertical linear layout manager. If you set this, @param <b>mode</b> is not be used.
 * @param click listener for menu items
 */
open class SheetMenu(
    var titleId: Int = 0,
    var title: String? = "",
    var menu: Int = 0,
    var layoutManager: RecyclerView.LayoutManager? = null,
    var adapter: MenuAdapter? = null,
    var click: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { false },
    var autoCancel: Boolean = true,
    var showIcons: Boolean = true
) {
    protected var dialog: BottomSheetDialog? = null
    protected var dialogLifecycleObserver: DialogLifecycleObserver? = null

    fun show(context: Context, lifecycle: Lifecycle) {
        dialogLifecycleObserver?.let {
            lifecycle.removeObserver(it)
        }
        dialogLifecycleObserver = DialogLifecycleObserver(showDialog(context)).also {
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

        processTitle(
            titleTextView = root.findViewById(R.id.text_title) as TextView,
            titleId = titleId,
            titleStr = title
        )

        val dialog = BottomSheetDialog(context).also {
            it.setContentView(root)
            processGrid(root, layoutManager)
        }
        this.dialog = dialog

        processRecycler(
            recycler = root.findViewById(R.id.recycler_view) as RecyclerView,
            dialog = dialog,
            menu = menu,
            layoutManager = layoutManager,
            adapter = adapter,
            click = click,
            autoCancel = autoCancel,
            showIcons = showIcons
        )

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet =
                dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
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

    protected open fun processGrid(root: View, layoutManager: RecyclerView.LayoutManager?) {
        if (
            root.findViewById<View>(R.id.text_title).visibility != View.VISIBLE &&
            layoutManager is GridLayoutManager
        ) {
            root.marginTop(24)
        }
    }

    protected open fun processTitle(
        titleTextView: TextView,
        titleId: Int,
        titleStr: String?
    ) = titleTextView.run {
        visibility = if (titleId > 0) {
            setText(titleId)
            View.VISIBLE
        } else if (!titleStr.isNullOrBlank()) {
            text = titleStr
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    protected open fun processRecycler(
        recycler: RecyclerView,
        dialog: BottomSheetDialog,
        menu: Int,
        layoutManager: RecyclerView.LayoutManager?,
        adapter: MenuAdapter?,
        click: MenuItem.OnMenuItemClickListener,
        autoCancel: Boolean,
        showIcons: Boolean
    ) {
        if (menu > 0) {
            recycler.adapter = adapter
                ?: MenuAdapter(
                    menuItems = recycler.context.inflate(menu).toList(),
                    callback = MenuItem.OnMenuItemClickListener {
                        click.onMenuItemClick(it)
                        if (autoCancel) dialog.cancel()
                        true
                    },
                    itemLayoutId = if (layoutManager is GridLayoutManager)
                        R.layout.item_grid
                    else
                        R.layout.item_linear,
                    showIcons = showIcons
                )
                    .also { this.adapter = it }

            recycler.layoutManager = layoutManager
                ?: LinearLayoutManager(recycler.context, RecyclerView.VERTICAL, false)
                    .also { this.layoutManager = it }
        }
    }

    protected open fun processClick(
        dialog: BottomSheetDialog,
        click: MenuItem.OnMenuItemClickListener,
        autoCancel: Boolean
    ): MenuItem.OnMenuItemClickListener =
        if (autoCancel)
            MenuItem.OnMenuItemClickListener {
                click.onMenuItemClick(it)
                dialog.cancel()
                true
            }
        else
            click

    //region java interop
    companion object {
        @JvmStatic
        fun with(context: Context) =
            Builder(context)
    }

    class Builder(private val context: Context) {
        private var title = ""
        private var menu: Int = 0
        private var layoutManager: RecyclerView.LayoutManager? = null
        private var adapter: MenuAdapter? = null
        private var click: MenuItem.OnMenuItemClickListener =
            MenuItem.OnMenuItemClickListener { false }
        private var autoCancel: Boolean = true
        private var showIcons: Boolean = true

        fun show() {
            SheetMenu(
                0,
                title,
                menu,
                layoutManager,
                null,
                click,
                autoCancel,
                showIcons
            ).show(context)
        }

        /**
         * @param titleId id from resources with text for title. it is more important than common <b>title</b> param
         */
        fun setTitle(titleId: Int): Builder {
            this.title = context.getString(titleId)
            return this
        }

        /**
         * @param title string with text for title
         */
        fun setTitle(text: String): Builder {
            this.title = text
            return this
        }

        /**
         * @param menu id from resources for auto-inflate menu
         */
        fun setMenu(menu: Int): Builder {
            this.menu = menu
            return this
        }

        /**
         * @param click listener for menu items
         */
        fun setClick(click: MenuItem.OnMenuItemClickListener): Builder {
            this.click = click
            return this
        }

        /**
         * @param layoutManager for RecyclerView. By default: vertical linear layout manager.
         */
        fun setLayoutManager(layoutManager: RecyclerView.LayoutManager?): Builder {
            this.layoutManager = layoutManager
            return this
        }

        /**
         * @param adapter for RecyclerView.
         * @see MenuAdapter by default
         */
        fun setAdapter(adapter: MenuAdapter): Builder {
            this.adapter = adapter
            return this
        }

        fun setAutoCancel(autoCancel: Boolean): Builder {
            this.autoCancel = autoCancel
            return this
        }

        fun showIcons(showIcons: Boolean): Builder {
            this.showIcons = showIcons
            return this
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

