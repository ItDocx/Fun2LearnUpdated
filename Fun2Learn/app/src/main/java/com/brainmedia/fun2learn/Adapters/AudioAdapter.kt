package com.brainmedia.fun2learn.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.brainmedia.fun2learn.Models.AudioModels
import com.brainmedia.fun2learn.PlayerActivity
import com.brainmedia.fun2learn.R
import com.bumptech.glide.Glide

class AudioAdapter(private val context:Context, private val audioList: ArrayList<AudioModels>):
    RecyclerView.Adapter<AudioAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemsView = LayoutInflater.from(context).
        inflate(R.layout.audio_items,parent,false)

        return MyViewHolder(itemView = itemsView)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val lisItems = audioList[position]
        holder.titleAudio.text = audioList[position].name

        holder.itemView.setOnClickListener{

            val intent = Intent(context,PlayerActivity::class.java)
            intent.putExtra("name", lisItems.name)
            intent.putExtra("url",lisItems.url)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return audioList.size
    }
    class MyViewHolder (itemView : View):RecyclerView.ViewHolder(itemView){

        val image:ImageView = itemView.findViewById(R.id.img_ic_audio)
        val titleAudio:TextView = itemView.findViewById(R.id.txt_audio)

    }


}