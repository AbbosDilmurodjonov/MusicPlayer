package uz.abbos_dilmurodjonov.musicplayer.utils

import android.app.Activity
import android.database.Cursor
import android.provider.MediaStore
import uz.abbos_dilmurodjonov.musicplayer.model.Music

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 12.03.2021.
 */
object Utils {

    fun allMusicList(activity: Activity): MutableList<Music> {

        val listOfAllMusic: MutableList<Music> = mutableListOf()

        val selection = "${MediaStore.Audio.Media.IS_MUSIC} != 0"
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Artists.ARTIST,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.DATA
        )
        val uriExternal = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI

        val cursor: Cursor? =
            activity.contentResolver.query(uriExternal, projection, selection, null, null)

        cursor?.let {
            val columnIndexID = it.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val columnIndexArtist = it.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST)
            val columnIndexTitle = it.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val columnIndexDuration = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val columnIndexData = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (it.moveToNext()) {
                val id = cursor.getLong(columnIndexID)
                val artist = cursor.getString(columnIndexArtist)
                val title = cursor.getString(columnIndexTitle)
                val duration = cursor.getLong(columnIndexDuration)
                val data = cursor.getString(columnIndexData)


                listOfAllMusic.add(Music(id, artist, title, duration, data))
            }

        }

        cursor?.close()
        return listOfAllMusic
    }
}