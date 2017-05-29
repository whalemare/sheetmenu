package ru.whalemare.sheetmenu

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import ru.whalemare.sheetmenu.extension.inflate

/**
 * Developed with ❤
 * @since 2017
 * @author Anton Vlasov - whalemare
 * @param titleId id from resources with text for title. it is more important than common <b>title</b> param
 * @param title string with text for title
 * @param menu id from resources for auto-inflate menu
 * @param click listener for menu items
 */
open class SheetMenu(
        var titleId: Int = 0,
        var title: String? = "",
        var menu: Int = 0,
        var click: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { false }) {

    fun show(context: Context) {
        val root = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_horizontal_list, null)

        val textTitle = root.findViewById(R.id.text_title) as TextView
        processTitle(textTitle)

        val recycler = root.findViewById(R.id.recycler_view) as RecyclerView
        processRecycler(recycler)

        BottomSheetDialog(context).apply {
            setContentView(root)
        }.show()
    }

    open protected fun processTitle(textTitle: TextView) {
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

    open protected fun processRecycler(recycler: RecyclerView) {
        if (menu > 0) {
            recycler.apply {
                adapter = ListAdapter.with(context.inflate(menu)).apply {
                    callback = click
                }
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    //region java interop
    companion object {
        @JvmStatic
        fun with(context: Context): SheetMenu.Builder {
            return SheetMenu.Builder(context)
        }
    }

    class Builder(val context: Context) {

        private var title = ""
        private var menu: Int = 0
        private var click: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { false }

        fun show() {
            SheetMenu(0, title, menu, click).show(context)
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
    }
    //endregion
}

