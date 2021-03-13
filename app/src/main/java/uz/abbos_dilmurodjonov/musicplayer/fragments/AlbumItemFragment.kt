package uz.abbos_dilmurodjonov.musicplayer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import uz.abbos_dilmurodjonov.musicplayer.R

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 08.03.2021.
 */
class AlbumItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.item_album, container, false)
    }
}