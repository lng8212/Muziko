package com.android.music.muziko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistSongsBinding
import com.android.music.muziko.adapter.PlaylistAdapter
import com.android.music.muziko.adapter.PlaylistSongAdapter
import com.android.music.muziko.viewmodel.PlaylistSongViewModel
import com.android.music.muziko.model.Song
import com.android.music.muziko.utils.SwipeToDelete
import com.android.music.ui.SongsRepository

class PlaylistSongsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistSongsBinding
    private val args by navArgs<PlaylistSongsFragmentArgs>()
    lateinit var playlistSongsAdapter: PlaylistSongAdapter
    lateinit var songsRepository: SongsRepository

    companion object {
        var viewModel: PlaylistSongViewModel? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistSongsBinding.inflate(inflater, container, false)
        songsRepository = context?.let { SongsRepository(it) }!!
        binding.constraintBackPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_playlistSongsFragment_to_playlistsFragment)
        }
        binding.txtNamePlaylistSong.text = args.myArg.name

        setupViewModel()
        playlistSongsAdapter = activity?.let { viewModel?.let { it1 -> PlaylistSongAdapter( it1.getDataset(), it) } }!!

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PlaylistSongViewModel::class.java)
        viewModel!!.setPlaylistId(args.myArg.id, songsRepository.getListOfSongs())
        viewModel!!.dataset.observe(viewLifecycleOwner, songUpdateObserver)
    }
    private val songUpdateObserver = Observer<ArrayList<Song>> {
        playlistSongsAdapter.listSong = it
        binding.recyclerviewPlaylistsSongs.adapter = playlistSongsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerviewPlaylistsSongs
        recyclerView.apply {
            adapter = playlistSongsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        swipeToDelete(recyclerView)

    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = playlistSongsAdapter.dataset[viewHolder.adapterPosition]
//                playlistSongsAdapter.dataset.remove(deletedItem)
                viewModel?.removeSongFromPlaylist(deletedItem.id.toString())
                playlistSongsAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}