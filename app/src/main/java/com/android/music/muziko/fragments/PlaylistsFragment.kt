package com.android.music.muziko.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentPlaylistsBinding
import com.android.music.muziko.adapter.PlaylistAdapter
import com.android.music.muziko.appInterface.VoidCallback
import com.android.music.muziko.helper.AnimationHelper
import com.android.music.muziko.model.Playlist
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
    ): View {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        binding.constraintPlaylistBackLibrary.setOnClickListener {
            AnimationHelper.scaleAnimation(it, object : VoidCallback {
                override fun execute() {
                    findNavController().navigate(R.id.action_playlistsFragment_to_navigation_library)
                }
            }, 0.95f)
        }

        binding.constraintAddPlaylist.setOnClickListener {
            AnimationHelper.scaleAnimation(it, object : VoidCallback {
                override fun execute() {
                    findNavController().navigate(R.id.action_playlistsFragment_to_addPlaylistsFragment)
                }
            }, 0.95f)
        }

        setupViewModel()

        playlistAdapter =
            activity?.let {
                viewModel?.let { it1 ->
                    PlaylistAdapter(
                        it1.getDataset() as ArrayList<Playlist>,
                        it,
                        this
                    )
                }
            }!!

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)
        context?.let { viewModel?.setFragmentContext(it) }
        viewModel!!.dataset.observe(viewLifecycleOwner, playlistUpdateObserver)
    }

    private val playlistUpdateObserver = Observer<ArrayList<*>> {
        playlistAdapter.dataset = it as ArrayList<Playlist>
        binding.recyclerviewPlaylistsLibrary.adapter = playlistAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.recyclerviewPlaylistsLibrary
        recyclerview.apply {
            adapter = playlistAdapter
            layoutManager = LinearLayoutManager(context)
        }

        playlistAdapter.onDataSend(
            object : PlaylistAdapter.OnDataSend {
                override fun onSend(context: Activity, id: String) {
                    viewModel?.updateData()
                }
            }
        )

        val mHandler = Handler()
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                viewModel?.updateData()
                mHandler.postDelayed(this, 1000)
            }
        })
    }


    override fun onItemClick(position: Int) {
        val playlist = playlistAdapter.arrayList[position]
        Log.e("id", playlist.id)
        Log.e("playlist", playlist.toString())
        val action =
            PlaylistsFragmentDirections.actionPlaylistsFragmentToPlaylistSongsFragment(playlist)
        findNavController().navigate(action)
    }


}