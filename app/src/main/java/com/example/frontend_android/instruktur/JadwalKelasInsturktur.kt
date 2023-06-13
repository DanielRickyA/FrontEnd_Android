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

import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentJadwalKelasInsturkturBinding
import com.example.frontend_android.response.presensiKelas.DataItem


import com.example.frontend_android.response.presensiKelas.ResponseKelasToday
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class JadwalKelasInsturktur : Fragment() {
    private var _binding : FragmentJadwalKelasInsturkturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentJadwalKelasInsturkturBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        getJadwalInstruktur()


    }

    fun getJadwalInstruktur(){
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.GetKelasInstrukturToday(
            "Bearer $token"
        ).enqueue(object : Callback<ResponseKelasToday>{
            override fun onResponse(
                call: Call<ResponseKelasToday>,
                response: Response<ResponseKelasToday>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                        loadRecycleView(responseBody.data as ArrayList<DataItem>)
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseKelasToday>, t: Throwable) {
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>){
        val adapter = JadwalKelasInstrukturAdapter(item)
        adapter.setCallback(object : JadwalKelasInstrukturAdapter.ClickInterface{
            override fun onClickItem(data: DataItem) {
                Toast.makeText(activity, "Berhasil", Toast.LENGTH_SHORT).show()
                sp!!.edit().putInt("id", data.id).apply()

                (activity as HomeInstrukturActivity).changeFragment(PresensiMember())
            }

        })
        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvJadwalInstruktur.adapter = adapter
    }


}