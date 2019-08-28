package ru.whalemare.sheetmenu.layout

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.whalemare.sheetmenu.LayoutProvider
import ru.whalemare.sheetmenu.R

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
class GridLayoutProvider(
    private val vertical: Boolean = true,
    private val spanCount: Int = 3,
    private val reverseLayout: Boolean = false
) : LayoutProvider {
    override fun provideLayoutManager(context: Context): RecyclerView.LayoutManager {
        val orientation = if (vertical) RecyclerView.VERTICAL else RecyclerView.HORIZONTAL
        return GridLayoutManager(context, spanCount, orientation, reverseLayout)
    }

    override fun provideItemLayoutRes(): Int {
        return R.layout.item_grid
    }


}