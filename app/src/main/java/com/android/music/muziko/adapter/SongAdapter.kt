package com.android.music.ui

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.android.music.R
import com.android.music.databinding.ItemSongBinding
import com.android.music.muziko.dialogs.SongDetailsDialog
import com.android.music.muziko.activity.MainActivity
import com.android.music.muziko.utils.ImageUtils
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.RoomRepository
import com.android.music.muziko.utils.SongUtils
import com.android.music.ui.fragments.SongFragment
import java.util.*
import kotlin.collections.ArrayList

class SongAdapter(var listSong : ArrayList<Song>, val context: Activity) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {
    var position = 0 // position of current item if click
    lateinit var dataSend: OnDataSend
    var listSearchSong : ArrayList<Song> = ArrayList()

    init {
        listSearchSong.addAll(listSong)
    }

    inner class SongViewHolder(private var binding: ItemSongBinding): RecyclerView.ViewHolder(binding.root){
        var title = binding.txtTitle
        var artist = binding.txtArtist
        var imageSong = binding.imgSong
        var itemRcv = binding.songContainer
        //var menuBtn = binding.musicMenuBtn
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
        @RequiresApi(Build.VERSION_CODES.R)
        fun onClickItem(){
            itemRcv.setOnClickListener{
                Log.e("adapter songs", "onClick")
                upDatePosition(adapterPosition)

                Coordinator.sourceOfSelectedSong = "songs"
                Coordinator.currentDataSource = listSong
                Coordinator.playSelectedSong(listSong[adapterPosition])
                RoomRepository.addSongToRecently(listSong[adapterPosition].id!!.toLong())
                MainActivity.activity.updateVisibility(listSong[adapterPosition])
            }
            binding.imgMenuItemSongs.setOnClickListener { it ->
                val popUpMenu = PopupMenu(context, it)
                popUpMenu.inflate(R.menu.songs_popup_menu)

                popUpMenu.setOnMenuItemClickListener {

                    return@setOnMenuItemClickListener handleMenuButtonClickListener(
                        it.itemId,
                        position = adapterPosition
                    )
                }
                popUpMenu.show()
            }
        }
    }

    fun getFilter(): Filter {
        return userFilter
    }

    private val userFilter =object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList: ArrayList<Song> = ArrayList()
            if(constraint == null || constraint.isEmpty()){
                Log.e("search","null text")
                listSearchSong.let{filteredList.addAll(it)}
                Log.e("list songs if null text", listSearchSong.size.toString())
                Log.e("filter list if null text", filteredList.size.toString())
            }
            else{
                val query = constraint.toString().trim().lowercase(Locale.getDefault())
                Log.d("Result", listSearchSong.size.toString())
                listSearchSong.forEach{
                    if(it.title?.toLowerCase(Locale.ROOT)?.contains(query) == true){
                        filteredList.add(it)
                    }
                }
                Log.e("list songs searched", filteredList.size.toString())
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if(results?.values is ArrayList<*>){
                listSong.clear()
                listSong.addAll(results.values as ArrayList<Song>)
                notifyDataSetChanged()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun handleMenuButtonClickListener(itemId: Int, position: Int): Boolean {
        when (itemId) {
            R.id.addToPlayList_menu_item -> {

                if(position>=0)
                {
                    dataSend.onSend(context, getSong(position))
                }

                else{
                    Toast.makeText(context, "please try again", Toast.LENGTH_SHORT).show()
                }

            }
            R.id.deleteFromDevice_menu_item -> {


                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                {
                    val songid = listSong[position].id

                    for (playlistId in songid?.let {
                        RoomRepository.listOfPlaylistsContainSpecificSong(
                            it
                        )
                    }!!)
                    {
                        RoomRepository.removeSongFromPlaylist(playlistId, songid.toString())
                    }

                    RoomRepository.removeSongFromFavorites(listSong[position])

                    getSongUri(position)?.let { SongUtils.deleteMusic(SongFragment.mactivity.baseContext, SongFragment.mactivity, it) }

                }
                else{
                    getSongUri(position)?.let { SongUtils.del(getSong(position).id.toString(), it) }
                }

            }
            R.id.details_menu_item -> {


                val songDetailsDialog = SongDetailsDialog(getSong(position))

                val manager: FragmentManager =
                    (context as AppCompatActivity).supportFragmentManager

                manager ?.beginTransaction()
                    ?.let { it -> songDetailsDialog.show(it, "songDetails") }


            }
            R.id.share_menu_item -> {
                SongUtils.shareMusic(context, getSong(position))
            }
//            R.id.setAsRingtone_menu_item -> {
//            }
            else -> return false
        }
        return true
    }

    fun getSong(position: Int): Song{
        if (position < 0) {

            Toast.makeText(context, "please try again!", Toast.LENGTH_SHORT).show()
            return Song()
        } else {
            return listSong[position]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongAdapter.SongViewHolder {
        var binding = ItemSongBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SongViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onBindViewHolder(holder: SongAdapter.SongViewHolder, position: Int) {
        val song = listSong[position]
        this.position = position
        holder.apply {
            bind(song)
            Log.e("adapter", "bind")
            onClickItem()
        }
//        holder.itemRcv.setOnClickListener {
//            Log.e("adapter songs", "onClick")
//            upDatePosition(getCurrentPosition())
//            Coordinator.setup(context)
//            Coordinator.sourceOfSelectedSong = "songs"
//            Coordinator.currentDataSource = listSong
//            Coordinator.playSelectedSong(listSong[getCurrentPosition()])
//            MainActivity.activity.updateVisibility()
//        }
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    fun upDatePosition(newPosition: Int){
        position = newPosition
    }

    fun getCurrentPosition():Int{
        return position
    }
    fun getSongUri(position: Int): Uri? {
        return listSong[position].uri
    }

    interface OnDataSend {
        fun onSend(context: Activity, song: Song)
    }

    fun OnDataSend(dataSend: OnDataSend) {
        this.dataSend = dataSend
    }
}