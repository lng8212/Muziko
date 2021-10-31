package com.android.music.muziko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentAddPlaylistsBinding
import com.android.music.muziko.adapter.AddPlaylistAdapter
import com.android.music.muziko.appInterface.PassDataForSelectPlaylists
import com.android.music.muziko.dialogs.AddSongToPlaylistDialog
import com.android.music.muziko.repository.RoomRepository
import com.android.music.muziko.utils.KeyboardUtils.hideKeyboard
import com.android.music.ui.Song
import com.android.music.ui.SongsRepository

class AddPlaylistsFragment : Fragment(), PassDataForSelectPlaylists {

    private lateinit var binding: FragmentAddPlaylistsBinding

    lateinit var songsRepository: SongsRepository
    lateinit var selectedSongs: ArrayList<Song>
    var addPlaylistAdapter: AddPlaylistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaylistsBinding.inflate(inflater, container, false)
        selectedSongs = ArrayList()
        binding.txtCancelAddPlaylistsFragment.setOnClickListener {

            try {
                selectedSongs.clear()
            } catch (exception: Exception){
                //TODO(handle the exception)
            }

            findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
        }

        binding.txtDoneAddPlaylistsFragment.setOnClickListener {

            if(binding.editTxtNamePlaylistsAddFragment.text.toString().trim().isEmpty()){
                binding.editTxtNamePlaylistsAddFragment.error = "Please enter a name"
            } else if (isUnique(binding.editTxtNamePlaylistsAddFragment.text.toString().uppercase())){
                binding.editTxtNamePlaylistsAddFragment.error = "Duplicate name"
            } else {
                var res = ""
                for(song in selectedSongs){
                    res += "${song.id},"
                }

                PlaylistsFragment.viewModel?.playlistRepository?.createPlaylist(binding.editTxtNamePlaylistsAddFragment.text.toString().uppercase(), selectedSongs.size, res)
                PlaylistsFragment.viewModel?.updateDataset()


                try {
                    selectedSongs.clear()
                } catch (exception: Exception){
                    //TODO(handle the exception)
                }

                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
            }
        }

        binding.constraintAddMusicAddFragment.setOnClickListener {
            createDialogToSelectPlaylist()
        }

        binding.layoutAddPlaylist.setOnClickListener {
            hideKeyboard(requireActivity())
        }
        return binding.root
    }

    fun createDialogToSelectPlaylist() {

        songsRepository = context?.let { SongsRepository(it) }!!

        val addSongToPlaylistDialog = AddSongToPlaylistDialog(songsRepository.getListOfSongs())

        addSongToPlaylistDialog?.setTargetFragment(this, 0)
        this.fragmentManager?.let { it1 -> addSongToPlaylistDialog?.show(it1, "pl") }

    }

    private fun isUnique(name: String): Boolean {
        for (playlist in RoomRepository.cachedPlaylistArray!!) {
            if (playlist.name == name)
                return true
        }
        return false
    }

    override fun passDataToInvokingFragment(songs: ArrayList<Song>) {
        selectedSongs = songs
        Log.e("Song", selectedSongs.toString())

        addPlaylistAdapter = activity?.let {
            AddPlaylistAdapter(
                it,
                selectedSongs
            )
        }

        binding.recyclerviewAddPlaylistsFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewAddPlaylistsFragment.adapter = addPlaylistAdapter


    }



}