package com.erayucar.kefabonelik.ui.base

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("NotifyDataSetChanged")
abstract class BaseAdapter<T, VH : BaseViewHolder<T>> : RecyclerView.Adapter<VH>() {

    private val items = mutableListOf<T>()

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): VH

    abstract fun findItemViewType(position: Int): Int

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return onCreateHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        return findItemViewType(position)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    fun updateItems(items: List<T>) {
        with(this.items) {
            clear()
            addAll(items)
        }

        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int) = items.getOrNull(position)
}