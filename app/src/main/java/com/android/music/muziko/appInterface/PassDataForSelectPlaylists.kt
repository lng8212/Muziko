package com.android.music.muziko.appInterface

import com.android.music.ui.Song


interface PassDataForSelectPlaylists {
    fun passDataToInvokingFragment(songs : ArrayList<Song>)
}