package uz.abbos_dilmurodjonov.musicplayer.fragments.main

import androidx.core.util.Consumer
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import uz.abbos_dilmurodjonov.musicplayer.BR
import uz.abbos_dilmurodjonov.musicplayer.model.Music
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 11.03.2021.
 */
class ItemUIController(val starter: Consumer<Long>) : BaseObservable() {
    var music: Music? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR._all)
        }

    fun selectMusic() {
        music?.let {
            starter.accept(it.id)
        }
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
    fun getDuration(): String {
        val date = Date(music?.duration ?: 0)
        val sdf = SimpleDateFormat("mm:ss", Locale.getDefault())

        return sdf.format(date)
    }

}