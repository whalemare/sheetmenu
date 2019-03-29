package ru.whalemare.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import ru.whalemare.sheetmenu.SheetMenu

open class MainActivityKotlin : AppCompatActivity() {

    var needTitle = false

    var needIcons = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById<CheckBox>(R.id.checkbox_title))
                .setOnCheckedChangeListener { _, isChecked -> needTitle = isChecked }

        (findViewById<CheckBox>(R.id.checkbox_icons))
                .setOnCheckedChangeListener { _, isChecked -> needIcons = isChecked }

        findViewById<View>(R.id.button_linear).setOnClickListener {
            setupLinear()
        }

        findViewById<View>(R.id.button_grid).setOnClickListener {
            setupGrid()
        }
    }

    fun setupLinear() {
        SheetMenu().apply {
            titleId = if (needTitle) R.string.title else 0
            click = MenuItem.OnMenuItemClickListener {
                toast("Click on ${it.title}")
                true
            }
            menu = R.menu.menu_icons
            showIcons = needIcons
        }.show(this)
    }

    fun setupGrid() {
        SheetMenu(
                titleId = if (needTitle) R.string.title else 0,
                menu = R.menu.menu_long_icons,
                layoutManager = GridLayoutManager(this, 3),
                click = MenuItem.OnMenuItemClickListener {
                    toast("Click on ${it.title}")
                    true
                },
                showIcons = needIcons
        ).show(this)
    }
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
