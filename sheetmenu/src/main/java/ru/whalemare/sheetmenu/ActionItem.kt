package ru.whalemare.sheetmenu

import android.graphics.drawable.Drawable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class ActionItem(
    val title: CharSequence,
    val image: Drawable? = null,
    val id: Int? = null
)