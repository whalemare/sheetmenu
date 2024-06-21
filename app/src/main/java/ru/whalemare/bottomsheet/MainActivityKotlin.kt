package ru.whalemare.bottomsheet

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.whalemare.sheetmenu.ActionItem
import ru.whalemare.sheetmenu.SheetMenu
import ru.whalemare.sheetmenu.layout.GridLayoutProvider

open class MainActivityKotlin : AppCompatActivity() {
    var needTitle = false

    var needIcons = true

    var needLong = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textMenu = findViewById<TextView>(R.id.textMenu)

        (findViewById<CheckBox>(R.id.checkbox_title))
            .setOnCheckedChangeListener { _, isChecked -> needTitle = isChecked }

        (findViewById<CheckBox>(R.id.checkbox_icons))
            .setOnCheckedChangeListener { _, isChecked ->
                needIcons = isChecked
                textMenu.text = when {
                    needIcons -> "R.layout.menu_icons"
                    !needIcons -> "R.layout.menu"
                    else -> "null"
                }
            }

        (findViewById<CheckBox>(R.id.checkbox_long))
            .setOnCheckedChangeListener { _, isChecked -> needLong = isChecked }

        findViewById<View>(R.id.button_linear).setOnClickListener {
            setupLinear()
        }

        findViewById<View>(R.id.button_grid).setOnClickListener {
            setupGrid()
        }

        findViewById<View>(R.id.button_menu).setOnClickListener {
            setupMenuRes()
        }
    }

    fun setupLinear() {
        SheetMenu(getSheetTitle(), getSheetItems()).show(this)
    }

    fun setupGrid() {
        SheetMenu(
            title = getSheetTitle(),
            actions = getSheetItems(),
            layoutProvider = GridLayoutProvider(),
            onClick = { item -> Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show() }
        ).show(this)
    }

    fun toast(text: CharSequence) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun setupMenuRes() {
        SheetMenu(
            context = this,
            title = getSheetTitle(),
            menu = if (needIcons) R.menu.menu_icons else R.menu.menu
        ).show(this)
    }

    fun setupJustTitles() {
        SheetMenu("Post", listOf("Send mail", "Send telegram", "Receive parcel")).show(this)
    }

    fun getSheetTitle() = if (needTitle) "Title" else null

    fun getSheetItems(): List<ActionItem>  {
        val size = if (needLong) 20 else 5
        return (0..size).map { index ->
            val image = if (needIcons) getRandomIcon() else null
            return@map ActionItem(index, "Title $index", image,R.color.colorAccent,R.drawable.ic_atom)
        }
    }

    fun getRandomIcon(): Drawable {
        val icons = listOf(R.drawable.ic_atom, R.drawable.ic_disco)
        return resources.getDrawable(icons.random())
    }

}