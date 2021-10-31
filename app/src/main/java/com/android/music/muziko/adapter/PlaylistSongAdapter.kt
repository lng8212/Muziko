package com.android.music.muziko.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.music.databinding.ItemPlaylistSongBinding
import com.android.music.muziko.utils.ImageUtils
import com.android.music.ui.Song

class PlaylistSongAdapter (var listSong: ArrayList<Song>, var context: Activity): RecyclerView.Adapter<PlaylistSongAdapter.PlaylistSongViewHolder>(){

    inner class PlaylistSongViewHolder(var binding: ItemPlaylistSongBinding): RecyclerView.ViewHolder(binding.root){
        var name = binding.txtNameItemPlaylistSong
        var artist = binding.txtArtistItemPlaylistSong
        var imageSong = binding.imgItemPlaylistSong
        fun bind(song: Song){
            name.text = song.title
            artist.text = song.artist
            song.image?.let {
                ImageUtils.loadImageToImageView(
                    context = context,
                    imageView = imageSong,
                    image = it
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistSongViewHolder {
        var binding = ItemPlaylistSongBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistSongViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistSongViewHolder, position: Int) {
        holder.bind(listSong[position])
    }

    override fun getItemCount(): Int {
        return listSong.size
    }


}