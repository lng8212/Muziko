package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.android.music.R
import com.android.music.databinding.FragmentArtistsBinding

class ArtistsFragment : Fragment() {

    private lateinit var binding: FragmentArtistsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistsBinding.inflate(inflater, container, false)

        binding.constraintArtistsBackLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_artistsFragment_to_navigation_library)
        }
        return binding.root
    }
}