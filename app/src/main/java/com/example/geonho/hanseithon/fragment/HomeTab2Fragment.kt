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
import com.example.geonho.hanseithon.adapters.FindAllRecyclerAdapter
import com.example.geonho.hanseithon.server.AllData
import com.example.geonho.hanseithon.server.FindAll
import com.example.geonho.hanseithon.server.ListService
import kotlinx.android.synthetic.main.fragment_home_tab2.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeTab2Fragment : Fragment() {

    lateinit var findallCardList : List<AllData>
    lateinit var recycleradapter:FindAllRecyclerAdapter
    lateinit var fragmentView : View

    companion object {

        @JvmStatic
        fun newInstance() = HomeTab2Fragment()
        val TAG : String = HomeTab2Fragment::class.java.simpleName
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home_tab2, container, false)
        loadData()
        return fragmentView
    }

    private fun loadData(){
        val listService : ListService = RetrofitUtil.retrofit.create(ListService::class.java)
        val call : Call<FindAll> = listService.findall()
        call.enqueue(object : Callback<FindAll> {
            override fun onFailure(call: Call<FindAll>?, t: Throwable?) {
                Log.e(TAG,t.toString())
                Toast.makeText(context,"데이터를 가져오는데 실패했습니다.",Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FindAll>?, response: Response<FindAll>?) {
                if(response?.body()!=null &&  response.body()!!.target.isEmpty()){
                    Toast.makeText(context,"가져올 수 있는 데이터가 없습니다!", Toast.LENGTH_SHORT).show()
                }
                findallCardList=response!!.body()!!.target
                setRecyclerView()
                recycleradapter.notifyDataSetChanged()
            }

        })
    }

    fun setRecyclerView(){
        recycleradapter = FindAllRecyclerAdapter(findallCardList, context!!)
        fragmentView.allfindRecyclerview.adapter = recycleradapter
        fragmentView.allfindRecyclerview.setHasFixedSize(true)
        fragmentView.allfindRecyclerview.layoutManager = LinearLayoutManager(context)
    }


}
