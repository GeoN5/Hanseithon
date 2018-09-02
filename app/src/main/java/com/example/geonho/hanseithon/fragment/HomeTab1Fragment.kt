package com.example.geonho.hanseithon.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.geonho.hanseithon.R
import com.example.geonho.hanseithon.RetrofitUtil
import com.example.geonho.hanseithon.adapters.LostAllRecyclerAdapter
import com.example.geonho.hanseithon.server.AllData
import com.example.geonho.hanseithon.server.ListService
import com.example.geonho.hanseithon.server.LostAll
import kotlinx.android.synthetic.main.fragment_home_tab1.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeTab1Fragment : Fragment() {

    lateinit var lostallCardList : List<AllData>
    lateinit var recycleradapter: LostAllRecyclerAdapter
    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = HomeTab1Fragment()
        val TAG : String = HomeTab1Fragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView =  inflater.inflate(R.layout.fragment_home_tab1, container, false)
        loadData()
        return fragmentView
    }

    private fun loadData(){
        val listService : ListService = RetrofitUtil.retrofit.create(ListService::class.java)
        val call : Call<LostAll> = listService.lostall()
        call.enqueue(object : Callback<LostAll> {
            override fun onFailure(call: Call<LostAll>?, t: Throwable?) {
                Log.e(TAG,t.toString())
                Toast.makeText(context,"데이터를 가져오는데 실패했습니다.",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<LostAll>?, response: Response<LostAll>?) {
                if(response?.body()!=null &&  response.body()!!.target.isEmpty()){
                    Toast.makeText(context,"가져올 수 있는 데이터가 없습니다!",Toast.LENGTH_SHORT).show()
                }
                lostallCardList=response!!.body()!!.target
                setRecyclerView()
                recycleradapter.notifyDataSetChanged()
            }

        })
    }

    fun setRecyclerView(){
        recycleradapter = LostAllRecyclerAdapter(lostallCardList, context!!)
        fragmentView.allLostRecyclerview.adapter = recycleradapter
        fragmentView.allLostRecyclerview.setHasFixedSize(true)
        fragmentView.allLostRecyclerview.layoutManager = LinearLayoutManager(context)
    }

}
