package com.android.music.muziko.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.android.music.R
import com.android.music.databinding.FragmentAddPlaylistsBinding
import com.android.music.muziko.repository.RoomRepository
import com.android.music.muziko.utils.KeyboardUtills.hideKeyboard

class AddPlaylistsFragment : Fragment(){

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

            if(binding.editTxtNamePlaylistsAddFragment.text.toString().trim().isEmpty()){
                binding.editTxtNamePlaylistsAddFragment.error = "Please enter a name"
            } else if (isUnique(binding.editTxtNamePlaylistsAddFragment.text.toString())){
                binding.editTxtNamePlaylistsAddFragment.error = "Duplicate name"
            } else {
                PlaylistsFragment.viewModel?.playlistRepository?.createPlaylist(binding.editTxtNamePlaylistsAddFragment.text.toString())

                PlaylistsFragment.viewModel?.updateDataset()

                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
            }
        }

        binding.constraintAddMusicAddFragment.setOnClickListener {

        }

        binding.layoutAddPlaylist.setOnClickListener {
            hideKeyboard(requireActivity())
        }
        return binding.root
    }

    private fun isUnique(name: String): Boolean {
        for (playlist in RoomRepository.cachedPlaylistArray!!) {
            if (playlist.name == name)
                return true
        }
        return false
    }

}