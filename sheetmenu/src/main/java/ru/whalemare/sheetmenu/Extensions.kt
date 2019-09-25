package ru.whalemare.sheetmenu

import android.annotation.SuppressLint
import android.content.Context
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.annotation.MenuRes
import androidx.appcompat.view.SupportMenuInflater
import androidx.appcompat.view.menu.MenuBuilder

/**
 * Developed by Magora-Systems.com
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
fun Menu.toList(): List<ActionItem> {
    val menuItems = ArrayList<ActionItem>(this.size())
    (0 until this.size()).mapTo(menuItems) {
        val item = this.getItem(it)
        return@mapTo ActionItem(item.itemId, item.title, item.icon)
    }
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