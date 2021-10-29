package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistsBinding
import com.android.music.muziko.adapter.PlaylistAdapter
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.viewmodel.PlaylistViewModel

class PlaylistsFragment : Fragment() {

    companion object {
        lateinit var binding: FragmentPlaylistsBinding
        lateinit var playlistAdapter: PlaylistAdapter
        lateinit var viewModel: PlaylistViewModel

        fun notifyDataSetChange(){
            viewModel.updateDataset()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        binding.constraintPlaylistBackLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_playlistsFragment_to_navigation_library)
        }

        binding.constraintAddPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_playlistsFragment_to_addPlaylistsFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)

        context?.let { viewModel.setFragmentContext(it) }

        val playlistUpdateObserver = Observer<ArrayList<Playlist>> {
            playlistAdapter.dataset = it
            binding.recyclerviewPlaylistsLibrary.adapter = playlistAdapter
        }
        viewModel.dataset.observe(viewLifecycleOwner, playlistUpdateObserver)
        playlistAdapter = activity?.let { viewModel.dataset.value?.let { it1 -> PlaylistAdapter(it1, it) } }!!
        val recyclerview = binding.recyclerviewPlaylistsLibrary
        recyclerview.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(context)
        }

        notifyDataSetChange()
    }
}