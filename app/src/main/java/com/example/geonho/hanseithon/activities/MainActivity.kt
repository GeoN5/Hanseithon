package com.example.geonho.hanseithon.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.widget.Toast
import com.example.geonho.hanseithon.R
import android.support.design.widget.BottomNavigationView
import com.example.geonho.hanseithon.fragment.HomeFragment
import com.example.geonho.hanseithon.fragment.MineFragment
import com.example.geonho.hanseithon.fragment.MypageFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0
    lateinit var fragment : Fragment
    lateinit var fragmentManager: FragmentManager

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init(){
        fragmentManager = supportFragmentManager
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        fragment = HomeFragment.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment).commit()
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime
        if (intervalTime in 0..FINSH_INTERVAL_TIME) {
            ActivityCompat.finishAffinity(this)
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

}
