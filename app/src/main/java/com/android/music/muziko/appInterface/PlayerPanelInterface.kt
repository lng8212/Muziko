package com.android.music.muziko.appInterface

import com.sothree.slidinguppanel.SlidingUpPanelLayout

interface PlayerPanelInterface {
    fun setSongImage()
    fun setSongTitle()

    //    for seekbar
    fun seekTo(mCurrentPosition: Int)
    fun seekbarHandler()
    fun setRemainingTime(remainingTime: Int)

    fun switchPlayPauseButton()  //change play btn to pause and vice versa
}