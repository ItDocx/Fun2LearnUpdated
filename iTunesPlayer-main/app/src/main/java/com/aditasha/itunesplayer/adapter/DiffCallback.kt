package com.aditasha.itunesplayer.adapter

import androidx.recyclerview.widget.DiffUtil
import com.aditasha.itunesplayer.network.ResultsItem

class DiffCallback(
    private val oldData: ArrayList<ResultsItem>,
    private val newData: ArrayList<ResultsItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].hashCode() == newData[newItemPosition].hashCode()
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition].trackId == newData[newItemPosition].trackId
    }
}