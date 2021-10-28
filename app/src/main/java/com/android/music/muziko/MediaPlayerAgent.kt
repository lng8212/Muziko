package com.android.music.muziko

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build

// class for controlling mediaPlayer
class MediaPlayerAgent(val context: Context) {
    fun playMusic(data: String) {
        val uri: Uri = Uri.parse(data)

        mediaPlayer.release()

        mediaPlayer = MediaPlayer.create(context, uri)
        mediaPlayer.start()
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O)
            playAsService()
    }

    private fun playAsService() {

    }

    private var mediaPlayer = MediaPlayer()

}
