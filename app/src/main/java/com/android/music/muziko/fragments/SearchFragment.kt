package com.android.music.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.DataBinderMapperImpl
import com.android.music.R
import com.android.music.databinding.FragmentSearchBinding
import com.android.music.muziko.appInterface.PassDataForSelectPlaylist
import com.android.music.muziko.dialogs.AddSongFromSongToPlaylistDialog
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.model.Song
import com.android.music.muziko.repository.RoomRepository
import com.android.music.ui.SongAdapter
import com.android.music.ui.SongViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchFragment : Fragment(), PassDataForSelectPlaylist {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SongAdapter
    lateinit var viewModel: SongViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongViewModel::class.java)
        context?.let { viewModel.setDataToFragment(it) }
        searchAdapter = activity?.let { viewModel.liveListSong.value?.let { it1 -> SongAdapter(it1, it) } }!!
        var rcv = binding.searchRv
        rcv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }

        searchAdapter.OnDataSend(
            object : SongAdapter.OnDataSend{
                override fun onSend(context: Activity, song: Song) {
                    SongFragment.selectedSong = song

                    if (RoomRepository.cachedPlaylistArray != null) {
                        if (RoomRepository.cachedPlaylistArray.size > 0) {
                            createDialogToSelectPlaylist()
                        } else {
                            val i = RoomRepository.cachedPlaylistArray
                            Toast.makeText(
                                requireActivity().baseContext,
                                getString(R.string.createPlaylist_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().baseContext,
                            getString(R.string.createPlaylist_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        )

        val searchView = binding.searchSongArtist
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.e("text submit", ".")
                searchAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.e("text change",newText.toString())
                searchAdapter.getFilter().filter(newText)
                return true
            }

        })
        notifyDataSetChange()
    }
    fun notifyDataSetChange(){
        viewModel.updateData()
    }

    fun createDialogToSelectPlaylist() {

        RoomRepository.updateCachedPlaylist()

        val addSongToPlaylistDialog = RoomRepository.cachedPlaylistArray?.let {
            AddSongFromSongToPlaylistDialog(
                it
            )
        }


        addSongToPlaylistDialog?.setTargetFragment(this, 0)
        this.fragmentManager?.let { it1 -> addSongToPlaylistDialog?.show(it1, "pl") }

    }

    override fun passDataToInvokingFragment(playlist: ArrayList<Playlist>) {
        SongFragment.selectedPlaylists = playlist

        addSongToSelectedPlaylist()

        SongFragment.selectedPlaylists.clear()
    }

    private fun addSongToSelectedPlaylist() {

        for (playlist in SongFragment.selectedPlaylists) {
            addSongToPlaylist(playlist)
        }
    }

    fun addSongToPlaylist(playlist: Playlist) {
        GlobalScope.launch {

            RoomRepository.addSongsToPlaylist(
                playlist.name,
                SongFragment.selectedSong.id.toString()
            )
        }
    }
}