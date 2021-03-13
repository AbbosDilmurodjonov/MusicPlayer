package uz.abbos_dilmurodjonov.musicplayer.fragments.player

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import uz.abbos_dilmurodjonov.musicplayer.R
import uz.abbos_dilmurodjonov.musicplayer.databinding.FragmentPlayerBinding
import uz.abbos_dilmurodjonov.musicplayer.model.Music
import uz.abbos_dilmurodjonov.musicplayer.utils.Utils


/**
 * Created by Abbos Dilmurodjonov (AyDee) on 12.03.2021.
 */
class PlayerFragment : Fragment(), PlayerNavigatorListener {
    companion object {
        private const val KEY_MUSIC_ID = "music.ID"

        fun newInstance(musicId: Long): PlayerFragment {
            val args = Bundle()
            args.putLong(KEY_MUSIC_ID, musicId)

            val fragment = PlayerFragment()
            fragment.arguments = args

            return fragment
        }
    }

    private var musicId: Long = -1
    private var position: Int = -1

    private var mMediaPlayer: MediaPlayer? = null
    private var dataBinding: FragmentPlayerBinding? = null

    private var listOfMusics: MutableList<Music>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            musicId = it[KEY_MUSIC_ID] as Long
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_player,
            container,
            false
        )

        listOfMusics = Utils.allMusicList(requireActivity())

        val controller = PlayerUIController(this)
        dataBinding?.controller = controller

        listOfMusics?.forEachIndexed { index, music ->
            if (music.id == musicId) {
                position = index
            }
        }

        if (position == 0) {
            dataBinding?.imagePrev?.isEnabled = false
            dataBinding?.imagePrev?.alpha = 0.5f
        }
        if (position == listOfMusics?.size?.minus(1) ?: 0) {
            dataBinding?.imageNext?.isEnabled = false
            dataBinding?.imageNext?.alpha = 0.5f
        }

        initMP()

        return dataBinding?.root
    }

    private fun initMP() {
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }

        dataBinding?.controller?.music = listOfMusics?.get(position)
        dataBinding?.seekBar?.max =
            (listOfMusics?.get(position)?.duration?.div(1_000))?.toInt() ?: 0

        val mHandler = Handler(Looper.getMainLooper())
        requireActivity().runOnUiThread(object : Runnable {
            override fun run() {
                if (mMediaPlayer != null) {
                    val mCurrentPosition = mMediaPlayer?.currentPosition?.div(1_000)
                    dataBinding?.seekBar?.progress = mCurrentPosition ?: 0

                    dataBinding?.controller?.curPos = mMediaPlayer?.currentPosition?.toLong() ?: 0L
                }
                mHandler.postDelayed(this, 1_000)
            }
        })

        dataBinding?.seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer?.seekTo(progress * 1_000)
                }
            }
        })

        val myUri: Uri = Uri.parse(listOfMusics?.get(position)?.data)
        mMediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(requireActivity(), myUri)
            setOnPreparedListener { it.start() }
            prepareAsync()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mMediaPlayer != null) {
            mMediaPlayer?.release()
            mMediaPlayer = null
        }
    }

    override fun next() {
        position++
        initMP()
        if (position >= listOfMusics?.size?.minus(1) ?: 0) {
            dataBinding?.imageNext?.isEnabled = false
            dataBinding?.imageNext?.alpha = 0.5f
        }

        if (position > 0) {
            dataBinding?.imagePrev?.isEnabled = true
            dataBinding?.imagePrev?.alpha = 1.0f
        }
    }

    override fun prev() {
        position--
        initMP()
        if (position < listOfMusics?.size?.minus(1) ?: 0) {
            dataBinding?.imageNext?.isEnabled = true
            dataBinding?.imageNext?.alpha = 1.0f
        }

        if (position == 0) {
            dataBinding?.imagePrev?.isEnabled = false
            dataBinding?.imagePrev?.alpha = 0.5f
        }
    }

    override fun pause() {
        mMediaPlayer?.pause()
    }

    override fun play() {
        mMediaPlayer?.start()
    }

}