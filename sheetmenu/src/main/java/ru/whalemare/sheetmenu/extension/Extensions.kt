package ru.whalemare.sheetmenu.extension

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.MenuRes
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.menu.MenuBuilder
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup


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

fun dp2px(context: Context, dp: Int): Int = (dp * context.resources.displayMetrics.density + 0.5).toInt()