package ru.whalemare.bottomsheet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

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
        SheetMenu(getSheetTitle(), getSheetItems()).show(this)
    }

    fun setupGrid() {
        SheetMenu(
            title = getSheetTitle(),
            items = getSheetItems(),
            layoutProvider = GridLayoutProvider()
        ).show(this)
    }

    fun getSheetTitle() = if (needTitle) "Title" else null

    fun getSheetItems() = listOf(
        ActionItem("Item 1"),
        ActionItem("Item 2"),
        ActionItem("Item 3")
    )
}

fun Context.toast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}
