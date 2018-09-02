package com.example.geonho.hanseithon.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.widget.Toast
import com.example.geonho.hanseithon.R
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.Menu
import android.view.MenuItem
import com.example.geonho.hanseithon.SharedPreferenceUtil
import com.example.geonho.hanseithon.fragment.*
import com.example.geonho.hanseithon.loadImage
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.nav_header_main.view.*


class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        supportFragmentManager.beginTransaction().add(R.id.content_main, MainFragment.newInstance()).commit()

        navigationHeaderset()
    }



    private fun navigationHeaderset(){
        nav_view.getHeaderView(0).imageView.loadImage("http://207.148.88.110:3000/uploads/${SharedPreferenceUtil.getData(applicationContext,"profile")}.jpg",this)
        nav_view.getHeaderView(0).name.text = SharedPreferenceUtil.getData(applicationContext,"username")!!
        nav_view.getHeaderView(0).email.text = SharedPreferenceUtil.getData(applicationContext,"email")!!
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_refresh, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.refresh -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_Home -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, HomeFragment.newInstance()).commit()
            }
            R.id.nav_Home_lost -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, HomeTab1Fragment.newInstance()).commit()
            }
            R.id.nav_Home_find -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, HomeTab2Fragment.newInstance()).commit()
            }
            R.id.nav_Mine -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, MineFragment.newInstance()).commit()
            }
            R.id.nav_Mine_lost -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, MineTab1Fragment.newInstance()).commit()
            }
            R.id.nav_Mine_find -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, MineTab2Fragment.newInstance()).commit()
            }
            R.id.nav_Mypage -> {
                supportFragmentManager.beginTransaction().replace(R.id.content_main, MypageFragment.newInstance()).commit()
            }
            R.id.nav_logout -> {
                SharedPreferenceUtil.removePreferences(this@MainActivity, "username")
                SharedPreferenceUtil.removePreferences(this@MainActivity, "token")
                SharedPreferenceUtil.removePreferences(this@MainActivity, "profile")
                SharedPreferenceUtil.removePreferences(this@MainActivity, "email")
                startActivity(Intent(this@MainActivity, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
