package ru.whalemare.sheetmenu

import android.graphics.drawable.Drawable

/**
 * @since 2019
 * @author Anton Vlasov - whalemare
 */
data class ActionItem(
    val title: String,
    val id: String? = null,
    val image: Drawable? = null
)