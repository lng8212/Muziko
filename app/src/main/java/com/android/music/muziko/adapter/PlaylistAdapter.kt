package com.android.music.muziko.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.music.R
import com.android.music.databinding.ItemPlaylistsLibraryBinding
import com.android.music.muziko.model.Playlist

class PlaylistAdapter (var context: Activity, arrayList: ArrayList<Playlist>) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>(){

    var dataset: ArrayList<Playlist>

    init {
        dataset = arrayList
    }

    inner class PlaylistViewHolder(var binding: ItemPlaylistsLibraryBinding): RecyclerView.ViewHolder(binding.root){
        var name_playlist = binding.txtNamePlaylist
        fun bind(playlist: Playlist){
            name_playlist.text = playlist.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        var binding = ItemPlaylistsLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.bind(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}