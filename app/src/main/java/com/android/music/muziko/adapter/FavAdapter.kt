package com.android.music.muziko.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.android.music.R
import com.android.music.databinding.ItemFavouriteBinding
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.utils.ImageUtils
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.RecentlyRepository
import com.android.music.muziko.repository.RoomRepository
import com.android.music.muziko.viewmodel.RecentlyViewModel
import com.android.music.ui.activity.MainActivity
import com.android.music.ui.fragments.LibraryFragment

class FavAdapter(var listSong: ArrayList<Song>, val context: Activity): RecyclerView.Adapter<FavAdapter.FavViewHolder>(){
    var position = 0
    inner class FavViewHolder(private var binding: ItemFavouriteBinding):RecyclerView.ViewHolder(binding.root){
        var title = binding.txtTitleItemFavourite
        var artist = binding.txtArtistItemFavourite
        var likeButton = binding.imgMenuItemFavourite
        var imageView = binding.imgItemFavourite
        fun bind (song : Song){
            title.text = song.title
            artist.text = song.artist
            song.image?.let {
                ImageUtils.loadImageToImageView(
                    context = context,
                    imageView = imageView,
                    image = it
                )
            }
        }
        fun onClickItem(){
            binding.songContainer.setOnClickListener {
                upDatePosition(adapterPosition)
                Coordinator.sourceOfSelectedSong = "fav"
                Coordinator.currentDataSource = listSong
                Coordinator.playSelectedSong(listSong[adapterPosition])
                RoomRepository.addSongToRecently(listSong[adapterPosition].id!!.toLong())
                MainActivity.activity.updateVisibility(listSong[adapterPosition])
            }
        }
        fun onCLickLike(){
            likeButton.setOnClickListener(){
                likeButton.setImageResource(R.drawable.ic_unfavorite)
//                RoomRepository.removeSongFromFavorites(listSong[adapterPosition])
            }

        }
    }
    fun upDatePosition(newPosition: Int){
        position = newPosition
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):FavAdapter.FavViewHolder {
        var binding = ItemFavouriteBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavAdapter.FavViewHolder, position: Int) {
        val song = listSong[position]
        this.position = position
        holder.apply {
            bind(song)
            onClickItem()
            onCLickLike()
        }
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    fun getCurrentPosition(): Int {
        return position
    }
}