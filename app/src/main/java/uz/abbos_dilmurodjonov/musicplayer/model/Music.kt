package uz.abbos_dilmurodjonov.musicplayer.model

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 11.03.2021.
 */
data class Music(
    val id: Long,
    val artist: String,
    val title: String,
    val duration: Long,
    val data: String,
)
