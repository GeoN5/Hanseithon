package com.example.geonho.hanseithon.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.example.geonho.hanseithon.R
import com.example.geonho.hanseithon.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lottie.playAnimation()

        Handler().postDelayed({

            if(SharedPreferenceUtil.getData(applicationContext, "token") ==null ||
                    SharedPreferenceUtil.getData(applicationContext, "token") !=null&&
                    SharedPreferenceUtil.getLogin(applicationContext,"login")==false){
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))

            }else if(SharedPreferenceUtil.getData(applicationContext, "token") !=null &&
                    SharedPreferenceUtil.getLogin(applicationContext,"login") == true){
                Toast.makeText(applicationContext, "자동로그인이 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            }
        },3000)

    }

}
