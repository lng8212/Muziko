package com.android.music.muziko

import android.content.Context
import com.android.music.muziko.`interface`.CoordinatorInterface
import com.android.music.ui.Song
import com.android.music.ui.fragments.LibraryFragment
import com.android.music.ui.fragments.SongFragment


// object for controlling play music
object Coordinator : CoordinatorInterface {
    override lateinit var nowPlayingQueue: ArrayList<Song>
    override lateinit var mediaPlayerAgent: MediaPlayerAgent
    override var position: Int = SongFragment.songAdapter.getCurrentPosition()
    var sourceOfSelectedSong = "songs" // source of current song, can be "playlist_name" or favourite
    var currentDataSource = arrayListOf<Song>() // list of songs to play

    override fun setup(context: Context) {
        mediaPlayerAgent = MediaPlayerAgent(context)
    }

    override fun playNextSong() {
        TODO("Not yet implemented")
    }

    override fun playPrevSong() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun play(song: String) {
        TODO("Not yet implemented")
    }

    override fun resume() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    override fun updateNowPlayingQueue() {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCurrentSongPosition(): Int {
        TODO("Not yet implemented")
    }

    override fun playSelectedSong(song: Song) {
        TODO("Not yet implemented")
    }

    override fun getPositionInPlayer(): Int {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean {
        TODO("Not yet implemented")
    }

    override fun hasPrev(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getPrevSongData(): String? {
        TODO("Not yet implemented")
    }

    override fun getNextSongData(): String? {
        TODO("Not yet implemented")
    }

    override fun getNextSong(): Song {
        TODO("Not yet implemented")
    }

    override fun getPrevSong(): Song {
        TODO("Not yet implemented")
    }

    override fun getSongAtPosition(position: Int): String? {
        TODO("Not yet implemented")
    }

    override fun seekTo(newPosition: Int) {
        TODO("Not yet implemented")
    }

}