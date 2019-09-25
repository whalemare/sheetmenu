package ru.whalemare.sheetmenu.layout

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.whalemare.sheetmenu.LayoutProvider
import ru.whalemare.sheetmenu.R

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class LinearLayoutProvider(
    private val vertical: Boolean = true,
    private val reverseLayout: Boolean = false
) : LayoutProvider {
    override fun provideLayoutManager(context: Context): RecyclerView.LayoutManager {
        val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
        return LinearLayoutManager(context, orientation, reverseLayout)
    }

    override fun provideItemLayoutRes(): Int =
        R.layout.item_linear
}