package com.android.music.muziko.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.music.databinding.FragmentPlayerPanelBinding
import com.android.music.muziko.appInterface.PlayerPanelInterface
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.utils.TimeUtils
import com.android.music.muziko.utils.ImageUtils
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class PlayerPanelFragment : Fragment(), PlayerPanelInterface {
    private lateinit var binding : FragmentPlayerPanelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Log.e("Player panel", "init")
        binding = FragmentPlayerPanelBinding.inflate(inflater,container,false)

        return binding.root
    }

    fun updatePanel() {
//        Log.e("Player panel", "update panel")
//        setSongTitle()
//        setSongImage()
//
//        binding.playerRemote.musicMax.text =
//            Coordinator.currentPlayingSong?.duration?.let {
//                TimeUtils.getReadableDuration(
//                    it
//                )
//            }
    }

    override fun setDefaultVisibilities() {
        TODO("Not yet implemented")
    }

    override fun setSongImage() {
        context?.let {
            ImageUtils.loadImageToImageView(
                it,
                binding.musicAlbumImage,
                Coordinator.currentPlayingSong?.image!!
            )
        }
    }

    override fun setSongTitle() {
        binding.musicTitleTv.text = Coordinator.currentPlayingSong?.title
    }

    override fun getPanelState() {
        TODO("Not yet implemented")
    }

    override fun setPanelState() {
        TODO("Not yet implemented")
    }

    override fun updateHeader() {
        TODO("Not yet implemented")
    }

    override fun seekTo(mCurrentPosition: Int) {
        TODO("Not yet implemented")
    }

    override fun seekbarHandler() {
        TODO("Not yet implemented")
    }

    override fun setRemainingTime(remainingTime: Int) {
        TODO("Not yet implemented")
    }

    override fun switchPlayPauseButton() {
        TODO("Not yet implemented")
    }

    override fun updatePanelBasedOnState(newState: SlidingUpPanelLayout.PanelState) {
        TODO("Not yet implemented")
    }

}