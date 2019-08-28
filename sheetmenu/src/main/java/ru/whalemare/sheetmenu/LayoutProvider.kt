package ru.whalemare.sheetmenu

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
interface LayoutProvider {
    fun provideLayoutManager(context: Context): RecyclerView.LayoutManager

    @LayoutRes
    fun provideItemLayoutRes(): Int
}