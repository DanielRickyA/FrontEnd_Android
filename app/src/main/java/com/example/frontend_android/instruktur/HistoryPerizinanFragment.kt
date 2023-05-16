package com.example.frontend_android.instruktur

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_android.HomeInstrukturActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentHistoryPerizinanBinding
import com.example.frontend_android.databinding.FragmentShowJadwalInstrukturBinding
import com.example.frontend_android.response.IjinInstruktur.DataItem
import com.example.frontend_android.response.IjinInstruktur.DataItemHistory
import com.example.frontend_android.response.IjinInstruktur.ReponseHistoryPerizinan
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HistoryPerizinanFragment : Fragment() {
    private var _binding: FragmentHistoryPerizinanBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryPerizinanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getHistoryPerizinan()

        binding.btnBack.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(ShowJadwalInstrukturFragment())
        }

    }

    fun getHistoryPerizinan(){
        val client = ApiConfig.getApiService()
        client.getHistoryPerizinan("Bearer $token").enqueue(object : Callback<ReponseHistoryPerizinan>{
            override fun onResponse(
                call: Call<ReponseHistoryPerizinan>,
                response: Response<ReponseHistoryPerizinan>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){

                        loadRecycleView(responseBody.data as ArrayList<DataItemHistory>)
                        println(responseBody.data)


                        Toast.makeText(activity, "Berhasil Mendapatkan Data", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(activity, "Gagal Mendapatkan Data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ReponseHistoryPerizinan>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItemHistory>){
        val adapter = HistoryPerizinanAdapter(item)

        binding.rvHistoryPerizinan.layoutManager = LinearLayoutManager(activity)
        binding.rvHistoryPerizinan.adapter = adapter

    }
}