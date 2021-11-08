package com.android.music.muziko.adapter

import android.app.Activity
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.music.databinding.ItemMusicOfArtistsBinding
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.model.Song
import com.android.music.muziko.utils.ImageUtils
import com.android.music.muziko.activity.MainActivity

class ArtistSongAdapter (var arrayList: ArrayList<Song>, val context: Activity) : RecyclerView.Adapter<ArtistSongAdapter.ArtistSongViewHolder>() {
    var dataset: ArrayList<Song>
    var position = 0

    init {
        dataset = arrayList
    }

    inner class ArtistSongViewHolder(var binding: ItemMusicOfArtistsBinding) : RecyclerView.ViewHolder(binding.root){
        var title = binding.txtTitleItemMusicOfArtist
        var artist = binding.txtArtistItemMusicOfArtist
        var imageSong = binding.imgItemMusicOfArtist
        var itemRcv = binding.songContainer
        fun bind(song: Song){
            title.text = song.title
            artist.text = song.artist
            song.image?.let {
                ImageUtils.loadImageToImageView(
                    context = context,
                    imageView = imageSong,
                    image = it
                )
            }
        }

        fun onClickItem(){
            itemRcv.setOnClickListener{
                Log.e("adapter songs", "onClick")
                upDatePosition(adapterPosition)

                Coordinator.sourceOfSelectedSong = "songs"
                Coordinator.currentDataSource = dataset
                Coordinator.playSelectedSong(dataset[adapterPosition])
                Coordinator.getSelectedSong(dataset[adapterPosition])
                MainActivity.activity.updateVisibility(dataset[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistSongViewHolder {
        var binding = ItemMusicOfArtistsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtistSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistSongViewHolder, position: Int) {
        val song = dataset[position]
        this.position = position
        holder.apply {
            bind(song)
            onClickItem()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun upDatePosition(newPosition: Int){
        position = newPosition
    }

    fun getCurrentPosition():Int{
        return position
    }
}