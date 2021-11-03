package com.android.music.muziko.activity

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.music.R
import com.android.music.databinding.ActivityPlayerPanelBinding
import com.android.music.muziko.appInterface.PlayerPanelInterface
import com.android.music.muziko.helper.Coordinator
import com.android.music.muziko.repository.RoomRepository
import com.android.music.muziko.utils.TimeUtils
import com.android.music.muziko.utils.ImageUtils
import com.android.music.ui.activity.MainActivity
import com.android.music.ui.activity.MainActivity.Companion.activity
import com.sothree.slidinguppanel.SlidingUpPanelLayout


class PlayerPanelActivity : AppCompatActivity(), PlayerPanelInterface,View.OnClickListener {
    private lateinit var binding : ActivityPlayerPanelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerPanelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        updatePanel()
        setOnEventListeners()
        seekbarHandler()
    }

    fun updatePanel() {
        RoomRepository.updateCachedFav()
        if(Coordinator.currentPlayingSong!! in RoomRepository.cachedFavArray){
            binding.playerRemote.favIcon.setImageResource(R.drawable.ic_favorite)
        }
        Log.e("Player panel", "update panel")
        setSongTitle()
        setSongImage()
        //binding.playerRemote.seekBar.max = Coordinator.currentPlayingSong!!.duration!!.toInt()
        Log.e("max",binding.playerRemote.seekBar.max.toString() )
        binding.playerRemote.musicMax.text =
            Coordinator.currentPlayingSong?.duration?.let {
                TimeUtils.getReadableDuration(
                    it
                )
            }
    }

    override fun setDefaultVisibilities() {
        TODO("Not yet implemented")
    }

    override fun setSongImage() {
        baseContext?.let {
            ImageUtils.loadImageToImageView(
                it,
                binding.musicAlbumImage,
                Coordinator.currentPlayingSong?.image!!
            )
        }
    }

    override fun setSongTitle() {
        binding.musicTitleTv.text = Coordinator.currentPlayingSong?.title
        binding.txtArtist.text = Coordinator.currentPlayingSong?.artist
    }
    fun setOnEventListeners() {
        binding.playerRemote.btnNext.setOnClickListener(this)
        binding.playerRemote.btnPrev.setOnClickListener(this)
        binding.playerRemote.playOrPauseLayout.setOnClickListener(this)
        binding.playerRemote.shuffleContainer.setOnClickListener(this)
        binding.playerRemote.repeatContainer.setOnClickListener(this)
        binding.playerRemote.favorContainer.setOnClickListener(this)
       // binding.playerRemote.seekBar.setOnSeekBarChangeListener(this)
        binding.playerRemote.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, percent: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    Log.e("seekbar max ", seekBar.max.toString())
                }
                if (Coordinator.isPlaying()) {
//                    if(fromUser){
                        // change the time when pull on seek bar
                        var newPercent = Coordinator.getPositionInPlayer().toFloat() / (Coordinator.currentPlayingSong?.duration?.toFloat()!!)
                        Log.e("percent", (newPercent).toString())
                        Log.e("time now", ((newPercent * TimeUtils.getDurationOfCurrentMusic()!!).toLong()).toString())
                        binding.playerRemote.musicMin.text = TimeUtils.getReadableDuration(
                            (newPercent * TimeUtils.getDurationOfCurrentMusic()!!).toLong()
                        )
//                    }

                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) { // set the music to the point in seek bar after pulling
                if (p0 != null) {
                    Log.e("time seek", (((p0.progress).toFloat()/100.0 * Coordinator.currentPlayingSong?.duration!!).toInt()).toString())
                    Coordinator.seekTo(((p0.progress).toFloat()/100.0 * Coordinator.currentPlayingSong?.duration!!).toInt())
                }
            }

        }
        )
        binding.imgBack.setOnClickListener{
//            val libraryFragment = LibraryFragment()
//            val fragmentManager: FragmentManager =
//            val transaction = fragmentManager.beginTransaction()
//            transaction.addToBackStack("playerPanel")
//            transaction.replace(
//                R.id.container,
//                MainActivity.playerPanelFragment,
//                "bottom sheet container"
//            )
//                .commit()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(v: View?) {
        when (v) {
//            binding.onExpand.likeIv -> {
//                if (Coordinator.currentPlayingSong!!.isFavorite()) {
//
//                    binding.onExpand.likeIv.setImageResource(R.drawable.ic_unfavored)
//                    RoomRepository.removeSongFromFavorites(Coordinator.currentPlayingSong!!)
//
//
//                } else {
//                    binding.onExpand.likeIv.setImageResource(R.drawable.ic_favored)
//
//                    RoomRepository.addSongToFavorites(Coordinator.currentPlayingSong!!.id ?: -1)
//
//                }
//            }

            binding.playerRemote.btnNext -> {
                Log.e("next", "next")
                Coordinator.playNextSong()
                updatePanel()
            }

            binding.playerRemote.favorContainer -> {
                RoomRepository.updateCachedFav()
                if(Coordinator.currentPlayingSong!! in RoomRepository.cachedFavArray) {
                    binding.playerRemote.favIcon.setImageResource(R.drawable.ic_unfavorite)
                    RoomRepository.removeSongFromFavorites(Coordinator.currentPlayingSong!!)
                }
                else{
                    binding.playerRemote.favIcon.setImageResource(R.drawable.ic_favorite)
                    RoomRepository.addSongToFavorites(Coordinator.currentPlayingSong!!.id!!.toLong())
                }


            }

            binding.playerRemote.btnPrev -> {
                Coordinator.playPrevSong()
                updatePanel()
            }

            binding.playerRemote.playOrPauseLayout -> {

                if (Coordinator.isPlaying()) {
                    Coordinator.pause()
                } else {
                    Coordinator.resume()
                }
                switchPlayPauseButton()
            }

//            binding.playerPanel.shuffle_container -> {
//                if (Coordinator.shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_NONE) {
//
//                    Coordinator.shuffleMode = PlaybackStateCompat.SHUFFLE_MODE_ALL
//
//                    binding.playerPanel.shuffle_container.displayedChild = 1
//
//                    Coordinator.updateNowPlayingQueue()
//
//                } else {
//
//                    Coordinator.shuffleMode = PlaybackStateCompat.SHUFFLE_MODE_NONE
//
//                    binding.playerPanel.shuffle_container.displayedChild = 2
//
//                    Coordinator.updateNowPlayingQueue()
//                }
//            }
//
//            binding.playerRemote.repeatContainer -> {
//                when (Coordinator.repeatMode) {
//                    PlaybackStateCompat.REPEAT_MODE_NONE -> {
//                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_ALL
//
//                        binding.playerPanel.repeatContainer.displayedChild = 1
//
//                        Coordinator.updateNowPlayingQueue()
//                    }
//
//                    PlaybackStateCompat.REPEAT_MODE_ALL -> {
//                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_ONE
//
//                        binding.playerPanel.repeatContainer.displayedChild = 2
//
//                        Coordinator.updateNowPlayingQueue()
//                    }
//
//                    PlaybackStateCompat.REPEAT_MODE_ONE -> {
//                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_NONE
//
//                        binding.playerPanel.repeatContainer.displayedChild = 3
//
//                        Coordinator.updateNowPlayingQueue()
//                    }
//                }
//            }

//            binding.header.onCollapse.play_btn_on_header -> {
//
//                Coordinator.resume()
//                binding.header.onCollapse.play_btn_on_header.visibility = View.GONE
//                binding.header.onCollapse.pause_btn_on_header.visibility = View.VISIBLE
//            }
//
//            binding.header.onCollapse.pause_btn_on_header -> {
//                Coordinator.pause()
//                binding.header.onCollapse.play_btn_on_header.visibility = View.VISIBLE
//                binding.header.onCollapse.pause_btn_on_header.visibility = View.GONE
//            }
        }
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
        binding.playerRemote.seekBar.progress = mCurrentPosition/2

    }

    override fun seekbarHandler() {
        val mHandler = Handler()
        activity.runOnUiThread(object : Runnable {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun run() {
                if (Coordinator.isPlaying()) {

                    val mCurrentPosition = Coordinator.getPositionInPlayer() / 1000
                    val duration = Coordinator.currentPlayingSong?.duration?.div(1000)

                    seekTo(mCurrentPosition)
                    setRemainingTime(mCurrentPosition)

                    if (mCurrentPosition == duration?.toInt()?.minus(3) ?: 0) {
                        Coordinator.takeActionBasedOnRepeatMode(
                            MainActivity.activity.getString(R.string.onSongCompletion),
                            MainActivity.activity.getString(R.string.play_next)
                        )
                    }
                }
                mHandler.postDelayed(this, 1000)
            }
        })
    }

    override fun setRemainingTime(remainingTime: Int) {
        binding.playerRemote.musicMin.text =
            TimeUtils.getReadableDuration((remainingTime * 1000).toLong())
    }

    override fun switchPlayPauseButton() {
        if (Coordinator.isPlaying()) {
            binding.playerRemote.btnPause.visibility = View.VISIBLE
            binding.playerRemote.btnPlay.visibility = View.GONE
        } else {
            binding.playerRemote.btnPause.visibility = View.GONE
            binding.playerRemote.btnPlay.visibility = View.VISIBLE
        }
    }

    override fun updatePanelBasedOnState(newState: SlidingUpPanelLayout.PanelState) {
        TODO("Not yet implemented")
    }

}