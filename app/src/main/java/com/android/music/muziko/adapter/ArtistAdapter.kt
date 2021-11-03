package com.android.music.muziko.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.music.databinding.ItemArtistsBinding
import com.android.music.muziko.model.Song
import com.android.music.muziko.utils.ImageUtils

class ArtistAdapter (var arrayList: ArrayList<Song>, val context: Activity, private val listener: OnItemClickListener) : RecyclerView.Adapter<ArtistAdapter.ArtistViewHoder>(){

    var dataset: ArrayList<Song>

    init {
        dataset = arrayList
    }


    inner class ArtistViewHoder (var binding: ItemArtistsBinding) : RecyclerView.ViewHolder(binding.root){
        var artist = binding.txtTitleItemArtist
        var image = binding.imgItemArtist
        fun bind(song: Song){
            artist.text = song.artist
            song.image?.let {
                ImageUtils.loadImageToImageView(
                    context = context,
                    imageView = image,
                    image = it
                )
            }
        }

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHoder {
        var binding = ItemArtistsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHoder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHoder, position: Int) {
        var artist = dataset[position]
        holder.bind(artist)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}