package com.android.music.ui.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.R
import com.android.music.databinding.FragmentFavouriteBinding
import com.android.music.muziko.adapter.FavAdapter
import com.android.music.muziko.viewmodel.FavViewModel
import com.android.music.ui.Song
import com.android.music.ui.SongViewModel

class FavouriteFragment : Fragment() {
    lateinit var binding: FragmentFavouriteBinding
    companion object{
        lateinit var viewModel: FavViewModel
        lateinit var favSongsAdapter: FavAdapter
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)
        viewModel!!.dataset.observe(viewLifecycleOwner, favSongsObserver)
        favSongsAdapter = context?.let {
            FavAdapter(
                viewModel.getDataset(),
                it as Activity
            )
        }!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.recyclerviewFavourite.layoutManager = LinearLayoutManager(context)
        viewModel.updateDataset()

        val mHandler = Handler()
        activity?.runOnUiThread(object : Runnable {
            override fun run() {
                viewModel?.updateDataset()
                mHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        viewModel.updateDataset()

    }


    private val favSongsObserver = Observer<ArrayList<Any>> { dataset ->
        favSongsAdapter?.listSong = dataset as ArrayList<Song>
        binding.recyclerviewFavourite.adapter = favSongsAdapter
    }
}