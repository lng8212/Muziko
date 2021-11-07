package com.android.music.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.DataBinderMapperImpl
import com.android.music.R
import com.android.music.databinding.FragmentSearchBinding
import com.android.music.muziko.model.Song
import com.android.music.ui.SongAdapter
import com.android.music.ui.SongViewModel

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchAdapter: SongAdapter
    private lateinit var listSearch : ArrayList<Song>
    lateinit var viewModel: SongViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater,container,true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongViewModel::class.java)
        searchAdapter = activity?.let { SongFragment.viewModel.liveListSong.value?.let { it1 -> SongAdapter(it1, it) } }!!
        var rcv = binding.searchRv
        rcv.apply {
            adapter = searchAdapter
            layoutManager = LinearLayoutManager(context)
        }
        val searchView = binding.searchSongArtist
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener, androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchAdapter.getFilter().filter(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchAdapter.getFilter().filter(newText)
                return true
            }

        })
        notifyDataSetChange()
    }
    fun notifyDataSetChange(){
        viewModel.updateData()
    }
}