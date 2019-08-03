package ru.whalemare.sheetmenu

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
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
 * @author Kirill Romanenko - indrih17
 * @author Hamid SN - hamidsn
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
    private var dialog: BottomSheetDialog? = null

    fun show(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_horizontal_list, null)

        val textTitle = root.findViewById(R.id.text_title) as TextView
        processTitle(textTitle)

        val dialog = BottomSheetDialog(context).also {
            it.setContentView(root)
            processGrid(root)
        }
        this.dialog = dialog

        val recycler = root.findViewById(R.id.recycler_view) as RecyclerView
        processRecycler(recycler, dialog)

        root.viewTreeObserver.addOnGlobalLayoutListener {
            val bottomSheet = dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)
            val behavior = BottomSheetBehavior.from(bottomSheet)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
            behavior.peekHeight = 0
        }
        dialog.show()
    }

    fun dismiss() {
        dialog?.dismiss()
        dialog = null
    }

    protected open fun processGrid(root: View) {
        if (root.findViewById<View>(R.id.text_title).visibility != View.VISIBLE) {
            if (layoutManager is GridLayoutManager) {
                root.marginTop(24)
            }
        }
    }

    protected open fun processTitle(textTitle: TextView) {
        if (titleId > 0) {
            textTitle.setText(titleId)
            textTitle.visibility = View.VISIBLE
        } else if (!title.isNullOrBlank()) {
            textTitle.text = title
            textTitle.visibility = View.VISIBLE
        } else {
            textTitle.visibility = View.GONE
        }
    }

    protected open fun processRecycler(recycler: RecyclerView, dialog: BottomSheetDialog) {
        if (menu > 0) {
            if (layoutManager == null) {
                layoutManager = LinearLayoutManager(recycler.context, RecyclerView.VERTICAL, false)
            }

            var itemLayoutId = R.layout.item_linear
            if (layoutManager is GridLayoutManager) {
                itemLayoutId = R.layout.item_grid
            }

            if (adapter == null) {
                adapter = MenuAdapter(
                    menuItems = recycler.context.inflate(menu).toList(),
                    callback = MenuItem.OnMenuItemClickListener {
                        click.onMenuItemClick(it)
                        if (autoCancel) dialog.cancel()
                        true
                    },
                    itemLayoutId = itemLayoutId,
                    showIcons = showIcons
                )
            }

            recycler.adapter = adapter
            recycler.layoutManager = layoutManager
        }
    }

    protected open fun processClick(dialog: BottomSheetDialog): MenuItem.OnMenuItemClickListener {
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

    //region java interop
    companion object {
        @JvmStatic
        fun with(context: Context): SheetMenu.Builder {
            return SheetMenu.Builder(context)
        }
    }

    class Builder(private val context: Context) {
        private var title = ""
        private var menu: Int = 0
        private var layoutManager: RecyclerView.LayoutManager? = null
        private var adapter: MenuAdapter? = null
        private var click: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { false }
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

