package com.android.music.muziko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistSongsBinding
import com.android.music.muziko.adapter.PlaylistSongAdapter
import com.android.music.muziko.viewmodel.PlaylistSongViewModel
import com.android.music.muziko.model.Song
import com.android.music.ui.SongsRepository

class PlaylistSongsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistSongsBinding
    private val args by navArgs<PlaylistSongsFragmentArgs>()
    lateinit var playlistSongsAdapter: PlaylistSongAdapter
    lateinit var viewModel: PlaylistSongViewModel
    lateinit var songsRepository: SongsRepository

    fun notifyDataSetChange(){
        viewModel.updateDataset()
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


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(PlaylistSongViewModel::class.java)
        Log.e("Playlist", args.myArg.id.toString())
        viewModel.setPlaylistId(args.myArg.id, songsRepository.getListOfSongs())

        val songUpdateObserver = Observer<ArrayList<Song>> {
            playlistSongsAdapter.listSong = it
            binding.recyclerviewPlaylistsSongs.adapter = playlistSongsAdapter
        }
        viewModel.dataset.observe(viewLifecycleOwner, songUpdateObserver)
        playlistSongsAdapter = activity.let { viewModel.dataset.value.let { it1 -> PlaylistSongAdapter(it1!!, it!!) } }
        val recyclerView = binding.recyclerviewPlaylistsSongs
        recyclerView.apply {
            adapter = playlistSongsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        notifyDataSetChange()
    }

}