package ru.whalemare.sheetmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView

/**
 * Developed with ❤
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
open class MenuAdapter(
    var menuItems: List<ActionItem> = emptyList(),
    var onClick: ((item: ActionItem) -> Unit)? = null,
    var itemLayoutId: Int = 0,
    var showIcons: Boolean = true
) : RecyclerView.Adapter<MenuAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = menuItems[position]

        holder.imageIcon.isVisible = showIcons && item.image != null
        holder.imageIcon.setImageDrawable(item.image)
        holder.textTitle.text = item.title
        holder.itemView.setOnClickListener {
            onClick?.invoke(item)
        }
    }

    override fun getItemCount(): Int = menuItems.size

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageIcon: ImageView = itemView.findViewById(R.id.image_icon) as ImageView
        val textTitle: TextView = itemView.findViewById(R.id.text_title) as TextView
    }
}