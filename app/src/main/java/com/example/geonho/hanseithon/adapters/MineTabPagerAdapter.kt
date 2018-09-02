package com.example.geonho.hanseithon.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.geonho.hanseithon.fragment.MineTab1Fragment
import com.example.geonho.hanseithon.fragment.MineTab2Fragment


class MineTabPagerAdapter(fm: FragmentManager?, val tabCount:Int) : FragmentStatePagerAdapter(fm) {

    var fragment = Fragment()
    private val tabTitles = arrayOf("Tab1", "Tab2")
    override fun getItem(position: Int): Fragment {

        // Returning the current tabs
        when (position) {
            0 -> {
                fragment = MineTab1Fragment()
            }
            1 -> {
                fragment = MineTab2Fragment()
            }

        }
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitles[position]
    }
    override fun getCount(): Int {
        return tabCount
    }
}