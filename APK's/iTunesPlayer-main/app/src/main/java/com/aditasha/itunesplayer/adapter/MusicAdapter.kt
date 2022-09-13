package com.aditasha.itunesplayer.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.aditasha.itunesplayer.GlideApp
import com.aditasha.itunesplayer.R
import com.aditasha.itunesplayer.databinding.ItemListSongsBinding
import com.aditasha.itunesplayer.network.ResultsItem

class MusicAdapter : RecyclerView.Adapter<MusicAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback
    private var listMusic: ArrayList<ResultsItem> = ArrayList()
    var playedRow = ArrayList<Int>()

    fun addData(data: ArrayList<ResultsItem>) {
        val diffCallback = DiffCallback(listMusic, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        playedRow.clear()

        listMusic.clear()
        listMusic.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemListSongsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listMusic[position]
        holder.bind(data, position)
    }

    inner class ListViewHolder(private var binding: ItemListSongsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: ResultsItem, position: Int) {
            val color = ContextCompat.getColor(itemView.context, R.color.light_blue)

            val circularProgressDrawable = CircularProgressDrawable(itemView.context)
            circularProgressDrawable.setColorSchemeColors(color)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 15f
            circularProgressDrawable.start()

            GlideApp.with(itemView.context)
                .load(data.artworkUrl100)
                .placeholder(circularProgressDrawable)
                .into(binding.albumArt)

            binding.songName.text = data.trackName
            binding.songArtist.text = data.artistName
            binding.songAlbum.text = data.collectionName

            binding.playingLogo.isVisible = playedRow.contains(position)

            itemView.setOnClickListener {
                var oldPosition = -1
                if (playedRow.isNotEmpty()) {
                    oldPosition = playedRow[0]
                }
                playedRow.clear()
                playedRow.add(position)
                notifyItemChanged(position)
                if (oldPosition > -1) notifyItemChanged(oldPosition)
                onItemClickCallback.onItemClicked(data, position)
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(music: ResultsItem, position: Int)
    }

    override fun getItemCount(): Int = listMusic.size
}