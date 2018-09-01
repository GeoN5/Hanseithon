package com.example.geonho.hanseithon.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.hanseithon.R
import kotlinx.android.synthetic.main.fragment_main.view.*
import android.support.design.widget.BottomNavigationView


class MainFragment : Fragment() {

    lateinit var fragmentView : View
    lateinit var fragment : Fragment
    lateinit var fragmentManager: FragmentManager

    companion object {

        @JvmStatic
        fun newInstance() = MainFragment()
        val TAG : String = MainFragment::class.java.simpleName
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_main, container, false)

        return fragmentView
    }

    private fun init(){
        fragmentManager = this
        fragmentView.bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragment = HomeFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.action_home -> {
                fragment = HomeFragment.newInstance()
            }
            R.id.action_mine -> {
                fragment = MineFragment.newInstance()
            }
            R.id.action_mypage -> {
                fragment = MypageFragment.newInstance()
            }
        }
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
        true
    }


}
