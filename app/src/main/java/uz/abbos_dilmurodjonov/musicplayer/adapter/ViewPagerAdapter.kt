package uz.abbos_dilmurodjonov.musicplayer.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * Created by Abbos Dilmurodjonov (AyDee) on 08.03.2021.
 */
class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    var list: List<Fragment>? = null

    override fun createFragment(position: Int): Fragment {
        return list?.get(position) ?: Fragment()
    }

    override fun getItemCount(): Int {
        return list?.size ?: 0
    }
}