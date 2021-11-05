package com.android.music.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.music.databinding.FragmentSongBinding
import com.android.music.muziko.model.Song
import com.android.music.ui.SongAdapter
import com.android.music.ui.SongViewModel

class SongFragment : Fragment() {
    companion object{
        lateinit var binding: FragmentSongBinding
        lateinit var songAdapter: SongAdapter
        lateinit var viewModel: SongViewModel
        lateinit var mactivity: FragmentActivity

        fun notifyDataSetChange(){
            viewModel.updateData()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSongBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongViewModel::class.java)

        context?.let { viewModel.setDataToFragment(it) }
        val updateListSong = Observer<ArrayList<Song>>{
            songAdapter.listSong = it
            binding.songsRv.adapter = songAdapter
        }
        viewModel.liveListSong.observe(viewLifecycleOwner, updateListSong)
        songAdapter = activity?.let { viewModel.liveListSong.value?.let { it1 -> SongAdapter(it1, it) } }!!
        val rcv = binding.songsRv
        rcv.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(context)
        }

        notifyDataSetChange()
    }
    override fun onResume() {
        super.onResume()

        viewModel.updateData()

        mactivity = requireActivity()

    }
}