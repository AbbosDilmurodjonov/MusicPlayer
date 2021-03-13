package uz.abbos_dilmurodjonov.musicplayer.service

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import uz.abbos_dilmurodjonov.musicplayer.model.Music

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 13.03.2021.
 */
private const val ACTION_PLAY: String = "com.example.action.PLAY"

class MusicService(val list: MutableList<Music>, val position: Int) : Service(),
    MediaPlayer.OnPreparedListener {
    private var mMediaPlayer: MediaPlayer? = null
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val myUri: Uri = Uri.parse(list[position].data)

        when (intent?.action) {
            ACTION_PLAY -> {
                mMediaPlayer = MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(applicationContext, myUri)
                    setOnPreparedListener(this@MusicService)
                    prepareAsync()
                }

            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onPrepared(mp: MediaPlayer) {
        mp.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
    }
}