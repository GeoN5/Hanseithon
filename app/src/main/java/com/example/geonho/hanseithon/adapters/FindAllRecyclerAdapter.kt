package com.example.geonho.hanseithon.adapters

import android.content.Context
import com.example.geonho.hanseithon.server.AllData
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.hanseithon.DateUtil
import com.example.geonho.hanseithon.R
import com.example.geonho.hanseithon.loadImage
import kotlinx.android.synthetic.main.item_find_cardview.view.*

class FindAllRecyclerAdapter(val items : List<AllData>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_find_cardview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: AllData = items[position]
        holder.itemView.date.text = DateUtil.formatDate(item.createDate)
        holder.itemView.image.loadImage("http://207.148.88.110:3000/uploads/${item.img}.jpg",context!!)
        holder.itemView.title.text = item.title
        holder.itemView.content.text = item.content
        holder.itemView.location.text = item.location
        holder.itemView.hashtag.text = item.tag
    }
}

class ViewHolder(itemView : View):RecyclerView.ViewHolder(itemView)