package ru.whalemare.sheetmenu

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import ru.whalemare.sheetmenu.extension.toList

/**
 * Developed with ❤
 * @since 2017
 * @author Anton Vlasov - whalemare
 */
open class ListAdapter(var menuItems: List<MenuItem> = emptyList(),
                       var callback: MenuItem.OnMenuItemClickListener? = null)
    : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    companion object {
        fun with(menu: Menu): ListAdapter {
            return ListAdapter(menu.toList())
        }
    }

    @LayoutRes
    open fun getLayoutItem(): Int {
        return R.layout.item_sheet_list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutItem(), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = menuItems[position]

        holder.imageIcon.setImageDrawable(item.icon)
        holder.textTitle.text = item.title
        holder.itemView.setOnClickListener {
            callback?.onMenuItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return menuItems.size
    }

    open class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        var imageIcon: ImageView = itemView?.findViewById(R.id.image_icon) as ImageView
        var textTitle: TextView = itemView?.findViewById(R.id.text_title) as TextView
    }
}