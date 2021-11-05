package com.android.music.muziko.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.support.v4.media.session.PlaybackStateCompat
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
        registerReceiver(broadcastNotificationReceiver, IntentFilter("Songs"))
        updatePanel()
        setOnEventListeners()
        seekbarHandler()

    }

    fun updatePanel() {
        RoomRepository.updateCachedFav()
        switchPlayPauseButton()
        RoomRepository.convertFavSongsToRealSongs()
        Log.e("update", Coordinator.currentPlayingSong!!.toString())
        var check = false
        for (i in RoomRepository.cachedFavArray) {
            if(Coordinator.currentPlayingSong!!.id == i.id){
                Log.e("Current",Coordinator.currentPlayingSong!!.toString())
                binding.playerRemote.favIcon.setImageResource(R.drawable.ic_favorite)
                check = true
            }
        }
        if (!check) binding.playerRemote.favIcon.setImageResource(R.drawable.ic_unfavorite)
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
//                    Log.e("seekbar max ", seekBar.max.toString())
                }
                if (Coordinator.isPlaying()) {
//                    if(fromUser){
                        // change the time when pull on seek bar
                        var newPercent = Coordinator.getPositionInPlayer().toFloat() / (Coordinator.currentPlayingSong?.duration?.toFloat()!!)
//                        Log.e("percent", (newPercent).toString())
//                        Log.e("time now", ((newPercent * TimeUtils.getDurationOfCurrentMusic()!!).toLong()).toString())
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
                RoomRepository.convertFavSongsToRealSongs()
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

            binding.playerRemote.shuffleContainer -> {
                if (Coordinator.shuffleMode == PlaybackStateCompat.SHUFFLE_MODE_NONE) {

                    Coordinator.shuffleMode = PlaybackStateCompat.SHUFFLE_MODE_ALL

                    binding.playerRemote.shuffleContainer.displayedChild = 1

                    Coordinator.updateNowPlayingQueue()

                } else {

                    Coordinator.shuffleMode = PlaybackStateCompat.SHUFFLE_MODE_NONE

                    binding.playerRemote.shuffleContainer.displayedChild = 2

                    Coordinator.updateNowPlayingQueue()
                }
            }

            binding.playerRemote.repeatContainer -> {
                when (Coordinator.repeatMode) {
                    PlaybackStateCompat.REPEAT_MODE_NONE -> {
                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_ALL

                        binding.playerRemote.repeatContainer.displayedChild = 1

                        Coordinator.updateNowPlayingQueue()
                    }

                    PlaybackStateCompat.REPEAT_MODE_ALL -> {
                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_ONE

                        binding.playerRemote.repeatContainer.displayedChild = 2

                        Coordinator.updateNowPlayingQueue()
                    }

                    PlaybackStateCompat.REPEAT_MODE_ONE -> {
                        Coordinator.repeatMode = PlaybackStateCompat.REPEAT_MODE_NONE

                        binding.playerRemote.repeatContainer.displayedChild = 3

                        Coordinator.updateNowPlayingQueue()
                    }
                }
            }

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

                    if (mCurrentPosition == duration?.toInt()?.minus(1) ?: 0) {
                        Coordinator.takeActionBasedOnRepeatMode(
                            MainActivity.activity.getString(R.string.onSongCompletion),
                            MainActivity.activity.getString(R.string.play_next)
                        )
                        updatePanel()
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

    private val broadcastNotificationReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.extras!!.getString(context.getString(R.string.extra_key))) {
                getString(R.string.notification_action_next) -> {
                    Log.e("player panel", "received")
                    updatePanel()
                }
                getString(R.string.notification_action_play) -> {
                    binding.playerRemote.btnPause.visibility = View.VISIBLE
                    binding.playerRemote.btnPlay.visibility = View.GONE

                }
                getString(R.string.notification_action_pause) -> {
                    binding.playerRemote.btnPause.visibility = View.GONE
                    binding.playerRemote.btnPlay.visibility = View.VISIBLE
                }
                getString(R.string.notification_action_previous) -> {
                    updatePanel()
                }
            }

        }
    }

    override fun onStart() {
        super.onStart()
        registerReceiver(broadcastNotificationReceiver, IntentFilter("Songs"))
    }

    override fun onResume() {
        super.onResume()
        updatePanel()
    }

    override fun onPause() {
        super.onPause()
        registerReceiver(broadcastNotificationReceiver, IntentFilter("Songs"))
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastNotificationReceiver)
    }
//    override fun onDestroy() {
//        super.onDestroy()
//        unregisterReceiver(broadcastNotificationReceiver)
//    }
}