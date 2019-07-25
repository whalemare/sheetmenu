package ru.whalemare.sheetmenu.extension

import android.annotation.SuppressLint
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.view.menu.MenuBuilder
import androidx.recyclerview.widget.RecyclerView
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.adapter.MenuAdapter

/**
 * Developed by Magora-Systems.com
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
fun Menu.toList(): List<MenuItem> {
    val menuItems = ArrayList<MenuItem>(this.size())
    (0 until this.size()).mapTo(menuItems) { this.getItem(it) }
    return menuItems
}

@SuppressLint("RestrictedApi")
fun Context.inflate(@MenuRes menuRes: Int): Menu {
    val menu = MenuBuilder(this)
    val menuInflater = SupportMenuInflater(this)
    menuInflater.inflate(menuRes, menu)
    return menu
}

fun View.marginBottom(dp: Int) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.bottomMargin = dp2px(this.context, dp)
}

fun View.marginTop(dp: Int) {
    val params = this.layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin = dp2px(this.context, dp)
    this.layoutParams = params
}

fun dp2px(context: Context, dp: Int): Int =
    (dp * context.resources.displayMetrics.density + 0.5).toInt()

fun Context.showSheetMenu(
    titleId: Int = 0,
    title: String? = "",
    menu: Int = 0,
    layoutManager: RecyclerView.LayoutManager? = null,
    adapter: MenuAdapter? = null,
    click: MenuItem.OnMenuItemClickListener = MenuItem.OnMenuItemClickListener { false },
    autoCancel: Boolean = true,
    showIcons: Boolean = true
): SheetMenu =
    SheetMenu(
        titleId,
        title,
        menu,
        layoutManager,
        adapter,
        click,
        autoCancel,
        showIcons
    ).also {
        it.show(this)
    }