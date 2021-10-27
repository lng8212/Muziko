package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

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

        return binding.root
    }

}