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
import com.example.frontend_android.databinding.FragmentShowJadwalInstrukturBinding
import com.example.frontend_android.response.IjinInstruktur.DataItem
import com.example.frontend_android.response.IjinInstruktur.ResponseJadwalInstruktur
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowJadwalInstrukturFragment : Fragment() {
    private var _binding: FragmentShowJadwalInstrukturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowJadwalInstrukturBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        getJadwalInstruktur()

        binding.btnHistory.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(HistoryPerizinanFragment())
        }
    }

    fun getJadwalInstruktur(){
        val client = ApiConfig.getApiService()
        client.getInstrukturHarian("Bearer $token" ).enqueue(object : Callback<ResponseJadwalInstruktur>{
            override fun onResponse(
                call: Call<ResponseJadwalInstruktur>,
                response: Response<ResponseJadwalInstruktur>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){

                        loadRecycleView(responseBody.data as ArrayList<DataItem>)
                        Toast.makeText(activity, "Berhasil Mendapatkan Data", Toast.LENGTH_SHORT).show()

                    }else{
                        Toast.makeText(activity, "Gagal Mendapatkan Data", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseJadwalInstruktur>, t: Throwable) {
                Toast.makeText(activity, "Internet Mati", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>){
        val adapter = ShowJadwalInstrukturAdapter(item)
        adapter.setCallback(object : ShowJadwalInstrukturAdapter.ClickInterface{
            override fun onClickItem(data: DataItem) {
                Toast.makeText(activity, "Berhasil", Toast.LENGTH_SHORT).show()
                sp!!.edit().putInt("id", data.id)
                    .putString("tanggal", data.tanggalJadwalHarian).apply()

                (activity as HomeInstrukturActivity).changeFragment(RequestIjinFragment())
            }

        })
        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvJadwalInstruktur.adapter = adapter

    }



}