package com.example.frontend_android.profil.profilInstruktur

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
import com.example.frontend_android.databinding.FragmentCekHistoryKelasInstrukturBinding
import com.example.frontend_android.instruktur.HistoryPerizinanAdapter
import com.example.frontend_android.response.IjinInstruktur.DataItemHistory
import com.example.frontend_android.response.profilInstruktur.DataItem
import com.example.frontend_android.response.profilInstruktur.ResponseHistoryKelasInstruktur
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekHistoryKelasInstruktur : Fragment() {
    private var _binding: FragmentCekHistoryKelasInstrukturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCekHistoryKelasInstrukturBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getHistoryKelasInstruktur()

        binding.btnBack.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(ProfilInstruktur())
        }

    }

    fun getHistoryKelasInstruktur(){
        val client = ApiConfig.getApiService()
        client.getHistoryKelasInstruktur(
            "Bearer $token",
        ).enqueue(object : Callback<ResponseHistoryKelasInstruktur> {
            override fun onResponse(
                call: Call<ResponseHistoryKelasInstruktur>,
                response: Response<ResponseHistoryKelasInstruktur>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                        loadRecycleView(responseBody.data as ArrayList<DataItem>)
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseHistoryKelasInstruktur>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>){
        val adapter = CekHistoryKelasInstrukturAdapter(item)

        binding.rvHistoryKelasInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvHistoryKelasInstruktur.adapter = adapter

    }
}