package ru.whalemare.sheetmenu

import android.graphics.drawable.Drawable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class ActionItem(
    val id: Int,
    val title: CharSequence,
    val image: Drawable? = null,
    val titleColor:Int? = null,
    val titleRightIcon:Int? = null
)