package com.android.music.ui.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentLibraryBinding
import com.android.music.muziko.adapter.RecentlyAdapter
import com.android.music.muziko.viewmodel.RecentlyViewModel
import com.android.music.muziko.model.Song
import com.android.music.ui.SongAdapter
import com.android.music.ui.SongViewModel

class LibraryFragment : Fragment() {
    private lateinit var binding: FragmentLibraryBinding
    companion object{
        const val DELETE_REQUEST_CODE = 2
        lateinit var viewModel: SongViewModel
        lateinit var recViewModel: RecentlyViewModel
        lateinit var recAdapter : RecentlyAdapter
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongViewModel::class.java)
        context?.let { viewModel.setDataToFragment(it) }
        recViewModel = ViewModelProvider(this).get(RecentlyViewModel::class.java)
        context?.let { recViewModel.sendDataToFragment() }
        recViewModel!!.dataset.observe(viewLifecycleOwner, recSongsObserver)
        recAdapter = activity?.let {
            recViewModel.dataset.value?.let {
                it1-> RecentlyAdapter(
                it1 as ArrayList<Song>,
                it
                )
            }
        }!!

        val recyclerView = binding.recyclerviewLibrary
        recyclerView.apply {
            adapter = recAdapter
            layoutManager = GridLayoutManager(context,2)
        }

        val mHandler = Handler()
        activity?.runOnUiThread(object : Runnable{
            override fun run() {
                recViewModel.updateData()
                mHandler.postDelayed(this,1000)
            }
        })
    }
    private val recSongsObserver = Observer<ArrayList<Any>> {
        recAdapter?.listSong = it as ArrayList<Song>
        binding.recyclerviewLibrary.adapter = recAdapter
    }
}