package ru.whalemare.bottomsheet

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.Toast
import ru.whalemare.sheetmenu.SheetMenu

open class MainActivityKotlin : AppCompatActivity() {

    var needTitle = false

    var needIcons = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById(R.id.checkbox_title) as CheckBox)
                .setOnCheckedChangeListener { _, isChecked -> needTitle = isChecked }

        (findViewById(R.id.checkbox_icons) as CheckBox)
                .setOnCheckedChangeListener { _, isChecked -> needIcons = isChecked }

        findViewById(R.id.button_linear).setOnClickListener({
            setupLinear()
        })

        findViewById(R.id.button_grid).setOnClickListener({
            setupGrid()
        })
    }

    fun setupLinear() {
        SheetMenu().apply {
            titleId = if (needTitle) R.string.title else 0
            click = MenuItem.OnMenuItemClickListener {
                toast("Click on ${it.title}")
                true
            }
            menu = if (needIcons) R.menu.menu_icons else R.menu.menu
        }.show(this)
    }

    fun setupGrid() {
        SheetMenu(
                titleId = if (needTitle) R.string.title else 0,
                menu = R.menu.menu,
                layoutManager = GridLayoutManager(this, 3),
                click = MenuItem.OnMenuItemClickListener {
                    toast("Click on ${it.title}")
                    true
                }
        ).show(this)
    }
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
