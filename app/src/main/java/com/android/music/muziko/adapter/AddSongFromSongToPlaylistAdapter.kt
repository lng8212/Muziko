package com.android.music.muziko.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.music.databinding.ItemAddSongFromSongToPlaylistsDialogBinding
import com.android.music.muziko.model.Playlist

class AddSongFromSongToPlaylistAdapter (var context: Activity, var list: ArrayList<Playlist>) : RecyclerView.Adapter<AddSongFromSongToPlaylistAdapter.AddSongFromSongToPlaylistViewHolder>(){

    companion object {
        var choices: ArrayList<Playlist> = arrayListOf()
    }

    inner class AddSongFromSongToPlaylistViewHolder(var binding: ItemAddSongFromSongToPlaylistsDialogBinding) : RecyclerView.ViewHolder(binding.root){
        var name = binding.textView2
        fun bind(playlist: Playlist){
            name.text = playlist.name
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AddSongFromSongToPlaylistViewHolder {
        var binding = ItemAddSongFromSongToPlaylistsDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  AddSongFromSongToPlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSongFromSongToPlaylistViewHolder, position: Int) {
        holder.bind(list[position])
        holder.binding.materialCheckBox.setOnClickListener {
            if(holder.binding.materialCheckBox.isChecked){
                choices.add(list[position])
            } else {
                choices.remove(list[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}