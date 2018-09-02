package com.example.geonho.hanseithon.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.geonho.hanseithon.fragment.HomeTab1Fragment
import com.example.geonho.hanseithon.fragment.HomeTab2Fragment

class HomeTabPagerAdapter(fm:FragmentManager?, val tabCount:Int) : FragmentStatePagerAdapter(fm) {

    var fragment = Fragment()
    private val tabTitles = arrayOf("lost", "find")
    override fun getItem(position: Int): Fragment {

        // Returning the current tabs
        when (position) {
            0 -> {
                fragment = HomeTab1Fragment()
            }
            1 -> {
                fragment = HomeTab2Fragment()
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

