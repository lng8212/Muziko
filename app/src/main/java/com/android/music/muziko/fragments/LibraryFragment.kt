package com.android.music.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.music.R
import com.android.music.databinding.FragmentLibraryBinding

class LibraryFragment : Fragment() {

    private lateinit var binding: FragmentLibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)

        binding.layoutPlaylistsLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_library_to_playlistsFragment)
        }

        binding.layoutArtistsLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_library_to_artistsFragment)
        }
        return binding.root
    }
}