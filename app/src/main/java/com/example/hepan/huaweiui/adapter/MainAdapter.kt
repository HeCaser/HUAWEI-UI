package com.example.hepan.huaweiui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hepan.huaweiui.R
import kotlinx.android.synthetic.main.item_main_adapter.view.*

class MainAdapter(val data: List<String>, private val listener: (Int) -> Unit) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.mainItem.setOnClickListener {
            listener(position)
        }
        val item = data[position].split("\n")
        with(item) {
            holder.itemView.tvTitle.text = get(0)
            holder.itemView.tvDesc.text = get(1)
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
}