package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.music.R
import com.android.music.databinding.FragmentAddPlaylistsBinding

class AddPlaylistsFragment : Fragment() {

    private lateinit var binding: FragmentAddPlaylistsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaylistsBinding.inflate(inflater, container, false)

        binding.txtCancelAddPlaylistsFragment.setOnClickListener {
            findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
        }

        binding.txtDoneAddPlaylistsFragment.setOnClickListener {
            findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
        }

        binding.constraintAddMusicAddFragment.setOnClickListener {

        }
        return binding.root
    }

}