package uz.abbos_dilmurodjonov.musicplayer.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import uz.abbos_dilmurodjonov.musicplayer.R
import uz.abbos_dilmurodjonov.musicplayer.adapter.RecyclerAdapter
import uz.abbos_dilmurodjonov.musicplayer.databinding.FragmentMusicListBinding
import uz.abbos_dilmurodjonov.musicplayer.databinding.ItemMusicBinding
import uz.abbos_dilmurodjonov.musicplayer.fragments.player.PlayerFragment
import uz.abbos_dilmurodjonov.musicplayer.model.Music
import uz.abbos_dilmurodjonov.musicplayer.utils.Utils

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 11.03.2021.
 */
class MainFragment : Fragment(), RecyclerAdapter.AdapterListener<Music> {

    private val adapter = RecyclerAdapter(R.layout.item_music, this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dataBinding =
            DataBindingUtil.inflate<FragmentMusicListBinding>(
                inflater,
                R.layout.fragment_music_list,
                container,
                false
            )

        dataBinding.recyclerMusicList.setHasFixedSize(true)
        dataBinding.recyclerMusicList.adapter = adapter

        adapter.list = Utils.allMusicList(requireActivity())

        return dataBinding.root
    }


    override fun setController(dataBinding: ViewDataBinding?) {
        val controller = ItemUIController {
            requireActivity().supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right,
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                .replace(R.id.layoutMainContainer, PlayerFragment.newInstance(it))
                .addToBackStack(null)
                .commit()

//            Intent(requireContext(), MusicService::class.java).also { intent ->
//                ContextCompat.startForegroundService(requireContext(), intent)
//            }


        }
        (dataBinding as ItemMusicBinding).controller = controller
    }

    override fun bindItem(dataBinding: ViewDataBinding?, item: Music) {
        (dataBinding as ItemMusicBinding).controller?.music = item
        dataBinding.executePendingBindings()
    }

}