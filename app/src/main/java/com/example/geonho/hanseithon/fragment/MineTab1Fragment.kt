package com.example.geonho.hanseithon.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.geonho.hanseithon.R


class MineTab1Fragment : Fragment() {

    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = MineTab1Fragment()
        val TAG : String = MineTab1Fragment::class.java.simpleName

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_mine_tab1, container, false)

        return fragmentView
    }


}
