package com.example.geonho.hanseithon.fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.geonho.hanseithon.R
import com.example.geonho.hanseithon.RetrofitUtil
import com.example.geonho.hanseithon.SharedPreferenceUtil
import com.example.geonho.hanseithon.activities.LoginActivity
import com.example.geonho.hanseithon.activities.MainActivity
import com.example.geonho.hanseithon.loadImage
import com.example.geonho.hanseithon.server.*
import kotlinx.android.synthetic.main.fragment_mypage.*
import kotlinx.android.synthetic.main.fragment_mypage.view.*
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import java.io.File

@Suppress("DEPRECATION")
class MypageFragment : Fragment(), EasyPermissions.PermissionCallbacks {

    var uri : Uri? = null
    lateinit var file : File
    private val REQUEST_GALLERY_CODE =200
    private val READ_REQUEST_CODE = 300
    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = MypageFragment()
        val TAG : String = MineFragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_mypage, container, false)
        loadData()
        setListener()
        return fragmentView
    }

    private fun setListener(){
        fragmentView.modifyButton.setOnClickListener {
            modify()
        }
        fragmentView.profileImage.setOnClickListener {
            image()
        }
        fragmentView.deleteTextView.setOnClickListener{
            delete()
        }
        fragmentView.basicText.setOnClickListener {
            profileImage.setImageDrawable(resources.getDrawable(R.drawable.basicprofile))
        }
    }

    private fun loadData(){
        val id = SharedPreferenceUtil.getData(context!!,"username")
        val userService:UserService = RetrofitUtil.retrofit.create(UserService::class.java)
        val call: Call<UserData> = userService.loadUser(id!!)
        call.enqueue(object :Callback<UserData>{
            override fun onFailure(call: Call<UserData>, t: Throwable) {
                Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<UserData>, response: retrofit2.Response<UserData>) {
                if(response.body()!=null) {
                    Log.d("asdf", response.body().toString())
                    fragmentView.profileImage.loadImage("http://207.148.88.110:3000/uploads/${response.body()!!.profile}.jpg",context!!)
                    fragmentView.password1EditText.setText(response.body()!!.pw)
                    fragmentView.phone.setText(response.body()!!.phone)
                    fragmentView.email.setText(response.body()!!.email)
                }
                }
            })

        }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Snackbar.make(fragmentView,"권한이 없습니다", Snackbar.LENGTH_SHORT).show()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
            image()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data!!.data//사진 data를 가져옴.
            if(EasyPermissions.hasPermissions(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                fragmentView.profileImage.loadImage(uri!!,context!!)//glide
                val filePath : String = getRealPathFromURI(uri!!,activity!!) //실제 path가 담김.
                file = File(filePath)
            }else{
                EasyPermissions.requestPermissions(this,"파일을 읽기 위해서는 권한이 필요합니다!",READ_REQUEST_CODE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }

        }
    }

    fun image(){
        val openGalleryIntent = Intent(Intent.ACTION_PICK)
        openGalleryIntent.type="image/*"
        startActivityForResult(openGalleryIntent,REQUEST_GALLERY_CODE)
    }

    private fun getRealPathFromURI(contentURI: Uri,activity: Activity):String{
        val cursor = activity.contentResolver.query(contentURI,null,null,null,null)
        //contentResolver라는 db에서 해당 URI를 탐색할수있는 cursor객체를 받아옴.
        return if(cursor ==null){
            contentURI.path
        }else{
            cursor.moveToFirst() //커서의 위치를 맨 앞인 첫 번째로 옮겨서
            val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)//data 컬럼의 인덱스를 가져옴.
            cursor.getString(idx) //해당하는 인덱스의 실제 path를 String으로 가져옴.
        }
    }

    private fun modify() {
        if (password1EditText.text.toString() == password2EditText.text.toString()) {
            val id: String? = SharedPreferenceUtil.getData(context!!, "username")
            val userService: UserService = RetrofitUtil.getLoginRetrofit(context!!).create(UserService::class.java)
            val modify = ModifyUser(password1EditText.text.toString(), phone.text.toString(), email.text.toString())
            val call: Call<Response> = userService.modifyUser(id!!,modify, RetrofitUtil.createRequestBody(file, "profile"))

            call.enqueue(object : Callback<Response> {
                override fun onFailure(call: Call<Response>?, t: Throwable?) {
                    Log.e(TAG, t.toString())
                    Toast.makeText(context, "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<Response>?, response: retrofit2  .Response<Response>?) {
                    if (response?.body() != null && response.body()!!.result.success) {
                        Toast.makeText(context, response.body()!!.result.message, Toast.LENGTH_LONG).show()
                        context!!.startActivity(Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    } else {
                        Toast.makeText(context, response!!.body()!!.result.message, Toast.LENGTH_LONG).show()
                    }
                }

            })
        } else {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                    .setTitle("경고").setMessage("입력하신 비밀번호를 확인해주세요!").setPositiveButton("확인")
                    { dialog, _ ->
                        dialog.dismiss()
                    }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun delete(){
        val id = SharedPreferenceUtil.getData(context!!,"username")
        val userService: UserService = RetrofitUtil.getLoginRetrofit(context!!).create(UserService::class.java)
        val call : Call<Response> = userService.delete(id!!)
        call.enqueue(object : Callback<Response> {
            override fun onFailure(call: Call<Response>?, t: Throwable?) {
                Log.e(TAG,t.toString())
            }

            override fun onResponse(call: Call<Response>?, response: retrofit2.Response<Response>?) {
                if(response!!.body()!=null && response.body()!!.result.success){
                    Toast.makeText(context,"성공적으로 삭제하였습니다",Toast.LENGTH_SHORT).show()
                    SharedPreferenceUtil.removePreferences(context!!,"id")
                    SharedPreferenceUtil.removePreferences(context!!,"token")
                    startActivity(Intent(context, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }
        })
    }
    }







