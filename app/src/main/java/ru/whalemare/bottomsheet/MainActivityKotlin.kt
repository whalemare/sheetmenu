package ru.whalemare.bottomsheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

open class MainActivityKotlin : AppCompatActivity() {
    private var needTitle = false

    private var needIcons = true

    private var needLong = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkbox_title.setOnCheckedChangeListener { _, isChecked -> needTitle = isChecked }

        checkbox_icons.setOnCheckedChangeListener { _, isChecked ->
            needIcons = isChecked
            textMenu.text = when {
                isChecked -> "R.layout.menu_icons"
                !isChecked -> "R.layout.menu"
                else -> "null"
            }
        }

        checkbox_long.setOnCheckedChangeListener { _, isChecked -> needLong = isChecked }

        button_linear.setOnClickListener {
            setupLinear()
        }

        button_grid.setOnClickListener {
            setupGrid()
        }

        button_menu.setOnClickListener {
            setupMenuRes()
        }
    }

    private fun setupLinear() {
        SheetMenu(getSheetTitle(), getSheetItems()).show(this)
    }

    private fun setupGrid() {
        SheetMenu(
            title = getSheetTitle(),
            actions = getSheetItems(),
            layoutProvider = GridLayoutProvider(),
            onClick = { item -> Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() }
        ).show(this)
    }

    private fun toast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    private fun setupMenuRes() {
        SheetMenu(
            context = this,
            title = getSheetTitle(),
            menu = if (needIcons) R.menu.menu_icons else R.menu.menu
        ).show(this)
    }

    private fun setupJustTitles() {
        SheetMenu("Post", listOf("Send mail", "Send telegram", "Receive parcel")).show(this)
    }

    private fun getSheetTitle() = if (needTitle) "Title" else null

    private fun getSheetItems(): List<ActionItem> {
        val size = if (needLong) 20 else 5
        return (0..size).map { index ->
            val image = if (needIcons) getRandomIcon() else null
            return@map ActionItem(index, "Title $index", image)
        }
    }

    private fun getRandomIcon(): Drawable? {
        val icons = listOf(R.drawable.ic_atom, R.drawable.ic_disco)
        return ContextCompat.getDrawable(this, icons.random())
    }
}