package com.android.music.muziko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistsBinding
import com.android.music.muziko.adapter.PlaylistAdapter
import com.android.music.muziko.model.Playlist
import com.android.music.muziko.utils.SwipeToDelete
import com.android.music.muziko.viewmodel.PlaylistViewModel

class PlaylistsFragment : Fragment(), PlaylistAdapter.OnItemClickListener {

    lateinit var binding: FragmentPlaylistsBinding
    lateinit var playlistAdapter: PlaylistAdapter

    companion object {
        var viewModel: PlaylistViewModel? = null
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

        setupViewModel()

        playlistAdapter =
            activity?.let { viewModel?.let { it1 -> PlaylistAdapter( it1.getDataSet(), it, this) } }!!

        return binding.root
    }

    private fun setupViewModel()
    {
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        context?.let { viewModel?.setFragmentContext(it) }
        viewModel!!.dataset.observe(viewLifecycleOwner, playlistUpdateObserver)
    }

    private val playlistUpdateObserver = Observer<ArrayList<Playlist>> {
        playlistAdapter.dataset = it
        binding.recyclerviewPlaylistsLibrary.adapter = playlistAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.recyclerviewPlaylistsLibrary
        recyclerview.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(context)
        }

        swipeToDelete(recyclerview)
    }


    override fun onItemClick(position: Int) {
        val playlist = playlistAdapter.arrayList[position]
        Log.e("id", playlist.id)
        Log.e("playlist", playlist.toString())
        val action = PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistSongsFragment(playlist)
        findNavController().navigate(action)
    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = playlistAdapter.dataset[viewHolder.adapterPosition]
                viewModel?.playlistRepository?.removePlaylist(deletedItem.id)
                playlistAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(context, "Delete", Toast.LENGTH_LONG).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}