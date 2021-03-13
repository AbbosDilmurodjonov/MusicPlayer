package uz.abbos_dilmurodjonov.musicplayer.fragments.player

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import uz.abbos_dilmurodjonov.musicplayer.BR
import uz.abbos_dilmurodjonov.musicplayer.model.Music
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 12.03.2021.
 */
class PlayerUIController(val navigator: PlayerNavigatorListener) : BaseObservable() {
    var isPlaying = false
    var curPos = 0L
        set(value) {
            field = value
            notifyPropertyChanged(BR.currentPosition)
        }
    var music: Music? = null
        set(value) {
            field = value
            isPlaying = true
            curPos = 0L
            notifyPropertyChanged(BR._all)
        }

    fun onNextClicked() {
        navigator.next()
    }

    fun onPrevClicked() {
        navigator.prev()
    }

    fun onPlayClicked() {
        isPlaying = true
        notifyPropertyChanged(BR.isPlaying)
        navigator.play()
    }

    fun onPauseClicked() {
        isPlaying = false
        notifyPropertyChanged(BR.isPlaying)
        navigator.pause()
    }

    @Bindable
    fun getIsPlaying(): Boolean {
        return isPlaying
    }

    @Bindable
    fun getArtist(): String {
        return music?.artist ?: ""
    }

    @Bindable
    fun getTitle(): String {
        return music?.title ?: ""
    }

    @Bindable
    fun getMaxDuration(): String {
        val date = Date(music?.duration ?: 0)
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())

        return sdf.format(date)
    }

    @Bindable
    fun getCurrentPosition(): String {
        val date = Date(curPos)
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())

        return sdf.format(date)
    }
}