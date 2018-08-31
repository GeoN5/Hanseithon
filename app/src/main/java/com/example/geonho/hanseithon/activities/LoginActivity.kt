package com.example.geonho.hanseithon.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.util.Log
import android.widget.Toast
import com.example.geonho.hanseithon.*
import com.example.geonho.hanseithon.server.LoginResponse
import com.example.geonho.hanseithon.server.User
import com.example.geonho.hanseithon.server.UserService
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private val FINSH_INTERVAL_TIME = 2000
    private var backPressedTime:Long = 0

    companion object {
        val TAG: String = LoginActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        setListeners()
    }

    private fun init(){
        SharedPreferenceUtil.saveLogin(applicationContext,"login",checkbox.isChecked)
    }

    private fun setListeners() {
        registerTextView.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }

        loginButton.setOnClickListener {
            val user = User(usernameEditText.text.toString(), passwordEditText.text.toString())
            val userService: UserService = RetrofitUtil.retrofit.create(UserService::class.java)
            val call: Call<LoginResponse> = userService.login(user)
            call.enqueue(object : Callback<LoginResponse> {
                override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                    Log.e(TAG,t.toString())
                    Toast.makeText(applicationContext, "네트워크 에러가 발생하였습니다!", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<LoginResponse>?, response: Response<LoginResponse>) {
                    if (response.body() != null && response.body()!!.result.success) {
                        Toast.makeText(applicationContext, "로그인에 성공하였습니다!", Toast.LENGTH_LONG).show()
                        SharedPreferenceUtil.saveData(applicationContext, "token", response.body()!!.auth.token)
                        SharedPreferenceUtil.saveData(applicationContext, "username", response.body()!!.user.id)
                        Log.d("token", SharedPreferenceUtil.getData(applicationContext, "token") + "is saved")
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    } else {
                        Toast.makeText(applicationContext, response.body()!!.result.message, Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
    }

    override fun onBackPressed() {
        val tempTime = System.currentTimeMillis()
        val intervalTime = tempTime - backPressedTime
        if (intervalTime in 0..FINSH_INTERVAL_TIME) {
            ActivityCompat.finishAffinity(this)
        } else {
            backPressedTime = tempTime
            Toast.makeText(applicationContext, "한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show()
        }
    }

}
