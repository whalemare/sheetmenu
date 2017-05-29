package ru.whalemare.sheetmenu.extension

import android.content.Context
import android.support.annotation.MenuRes
import android.support.v7.view.SupportMenuInflater
import android.support.v7.view.menu.MenuBuilder
import android.view.Menu
import android.view.MenuItem

/**
 * Developed by Magora-Systems.com
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
fun Menu.toList(): List<MenuItem> {
    val menuItems = ArrayList<MenuItem>(this.size())
    (0..this.size() - 1).mapTo(menuItems) { this.getItem(it) }
    return menuItems
}

fun Context.inflate(@MenuRes menuRes: Int): Menu {
    val menu = MenuBuilder(this)
    val menuInflater = SupportMenuInflater(this)
    menuInflater.inflate(menuRes, menu)
    return menu
}