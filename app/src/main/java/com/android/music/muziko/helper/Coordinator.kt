package com.android.music.muziko.helper

import android.content.Context
import android.util.Log
import com.android.music.muziko.appInterface.CoordinatorInterface
import com.android.music.muziko.model.Song


// object for controlling play music
object Coordinator : CoordinatorInterface {
    override lateinit var nowPlayingQueue: ArrayList<Song>
    override lateinit var mediaPlayerAgent: MediaPlayerAgent
    override var position: Int = -1
//        SongFragment.songAdapter.getCurrentPosition() ?: -1 // position of song in this queue
    var sourceOfSelectedSong =
        "songs" // source of current song, can be "playlist_name" or favourite
    var currentDataSource = arrayListOf<Song>() // list of songs to play

    var currentPlayingSong: Song? = null // song is playing
        set(value) {
            field = value
            //MainActivity.playerPanelFragment.updatePanel()
        }

    override fun setup(context: Context) { // set up mediaPlayer
        mediaPlayerAgent = MediaPlayerAgent(context)
    }

    override fun playNextSong() {
        TODO("Not yet implemented")
    }

    override fun playPrevSong() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        mediaPlayerAgent.pauseMusic()
    }

    override fun play(song: String) {
        Log.e("media", "play")
        mediaPlayerAgent.playMusic(song)
    }

    override fun resume() {
        mediaPlayerAgent.resumePlaying()
    }

    override fun stop() {
        mediaPlayerAgent.stop()
    }

    override fun release() {
        TODO("Not yet implemented")
    }

    override fun updateNowPlayingQueue() {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        return mediaPlayerAgent.isPlaying()
    }

    override fun getCurrentSongPosition(): Int {
        TODO("Not yet implemented")
    }

    override fun playSelectedSong(song: Song) {
        Log.e("Coordinator", "play")
        updatePlayerVar(song)
        //updateNowPlayingQueue()
        song.data?.let { play(it) }
    }
    fun getSelectedSong(song: Song): Song {
        return song
    }
    fun updatePlayerVar(song: Song) {
        currentPlayingSong = song
        Log.e("song ", currentPlayingSong.toString())
//        MainActivity.playerPanelFragment.updatePanel()
    }

    override fun getPositionInPlayer(): Int {
        return mediaPlayerAgent.getCurrentPosition()
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
        mediaPlayerAgent.seekTo(newPosition)
    }

}