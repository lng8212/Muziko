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

    var playlistAdapter: PlaylistAdapter? = null

    companion object {
        var viewModel: PlaylistViewModel? = null
    }

    private lateinit var binding: FragmentPlaylistsBinding
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

        setupViewModel()

        playlistAdapter = activity?.let {
            PlaylistAdapter(
                it,
                viewModel?.dataset?.value as ArrayList<Playlist>
            )
        }

        return binding.root
    }

    private fun setupViewModel()
    {
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        context?.let { viewModel?.setFragmentContext(it) }
        viewModel!!.dataset.observe(viewLifecycleOwner, playlistUpdateObserver)
        val recyclerview = binding.recyclerviewPlaylistsLibrary
        recyclerview.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private val playlistUpdateObserver = Observer<ArrayList<Any>> { dataset ->
        playlistAdapter?.dataset = dataset as ArrayList<Playlist>
        binding.recyclerviewPlaylistsLibrary.adapter = playlistAdapter
    }

}