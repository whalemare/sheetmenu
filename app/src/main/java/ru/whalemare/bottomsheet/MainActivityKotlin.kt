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

    var removeSomeItems = false

    private var sheetMenu: SheetMenu? = null

    // remove all items where index % 2 == 0
    private val removeEven: (List<MenuItem>) -> List<MenuItem> = { items -> items.filterIndexed { index, menuItem -> index % 2 == 0 }}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (findViewById<CheckBox>(R.id.checkbox_title))
                .setOnCheckedChangeListener { _, isChecked -> needTitle = isChecked }

        (findViewById<CheckBox>(R.id.checkbox_icons))
                .setOnCheckedChangeListener { _, isChecked -> needIcons = isChecked }

        (findViewById<CheckBox>(R.id.checkbox_dynamic))
            .setOnCheckedChangeListener { _, isChecked -> removeSomeItems = isChecked }

        findViewById<View>(R.id.button_linear).setOnClickListener {
            setupLinear()
        }

        findViewById<View>(R.id.button_grid).setOnClickListener {
            setupGrid()
        }
    }

    override fun onPause() {
        super.onPause()
        sheetMenu?.dismiss()
    }

    fun setupLinear() {
        sheetMenu = SheetMenu().apply {
            titleId = if (needTitle) R.string.title else 0
            click = MenuItem.OnMenuItemClickListener {
                toast("Click on ${it.title}")
                true
            }
            menu = R.menu.menu_icons
            showIcons = needIcons
            mapMenuItems = if (removeSomeItems) removeEven else { items -> items }
        }
        sheetMenu?.show(this)
    }

    fun setupGrid() {
        sheetMenu = SheetMenu(
            titleId = if (needTitle) R.string.title else 0,
            menu = R.menu.menu_long_icons,
            layoutManager = GridLayoutManager(this, 3),
            click = MenuItem.OnMenuItemClickListener {
                toast("Click on ${it.title}")
                true
            },
            showIcons = needIcons,
            mapMenuItems = if (removeSomeItems) removeEven else { items -> items }
        )
        sheetMenu?.show(this)
    }
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
