package com.example.geonho.hanseithon.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.hanseithon.R
import com.example.geonho.hanseithon.adapters.MineTabPagerAdapter
import kotlinx.android.synthetic.main.fragment_mine.*
import kotlinx.android.synthetic.main.fragment_mine.view.*

class MineFragment : Fragment() {

    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = MineFragment()
        val TAG : String = MineFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_mine, container, false)

        setTablayout()
        setViewpager()
        return fragmentView
    }

    private fun setTablayout(){
        val pager=MineTabPagerAdapter(fragmentManager,2)
        fragmentView.tab.getTabAt(0)!!.text =pager.getPageTitle(0)
        fragmentView.tab.getTabAt(1)!!.text = pager.getPageTitle(1)
        fragmentView.tab.tabGravity = TabLayout.GRAVITY_FILL
    }

    private fun setViewpager(){
        val pagerAdapter = MineTabPagerAdapter(fragmentManager, tab.tabCount)
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab))

        // Set TabSelectedListener
        tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

        })

    }

}
