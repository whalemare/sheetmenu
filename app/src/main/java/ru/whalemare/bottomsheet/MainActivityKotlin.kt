package ru.whalemare.bottomsheet

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import ru.whalemare.sheetmenu.SheetMenu

open class MainActivityKotlin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.button_show_menu).setOnClickListener({
            setup()
        })
    }

    fun setup() {
        SheetMenu().apply {
            titleId = R.string.title
            layoutManager = GridLayoutManager(this@MainActivityKotlin, 2)
            click = MenuItem.OnMenuItemClickListener { true }
            menu = R.menu.menu
        }.show(this)
    }

    fun setup2() {
        SheetMenu(
                menu = R.menu.menu,
                titleId = R.string.title,
                click = MenuItem.OnMenuItemClickListener { true }
        ).show(this)
    }
}
