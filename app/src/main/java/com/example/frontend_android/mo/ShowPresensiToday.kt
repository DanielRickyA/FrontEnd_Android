package com.example.frontend_android.mo

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_android.HomePegawaiActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentShowPresensiBinding
import com.example.frontend_android.databinding.FragmentShowPresensiTodayBinding
import com.example.frontend_android.response.presensiInstruktur.DataItemPresensi
import com.example.frontend_android.response.presensiInstruktur.ResponsePresensi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowPresensiToday : Fragment() {
    private var _binding: FragmentShowPresensiTodayBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var presensi: SharedPreferences? = null
    var token: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentShowPresensiTodayBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        presensi = requireActivity().getSharedPreferences("presensiId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getPresensiToday()

    }
    fun getPresensiToday(){
        val client = ApiConfig.getApiService()
        client.getPresensiAllToday("Bearer $token").enqueue(object : Callback<ResponsePresensi> {
            override fun onResponse(
                call: Call<ResponsePresensi>,
                response: Response<ResponsePresensi>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        loadRecycleView(responseBody.data as ArrayList<DataItemPresensi>)

                    }
                }else{
                    Toast.makeText(activity, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponsePresensi>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItemPresensi>){
        val adapter = ShowPresensiAdapter(item)
        adapter.setCallback(object : ShowPresensiAdapter.ClickInterface{
            @SuppressLint("CommitPrefEdits")
            override fun onClickItem(data: DataItemPresensi){
                Toast.makeText(activity, "Berhasil", Toast.LENGTH_SHORT).show()
                sp!!.edit().putInt("id", data.id)
                    .putString("tanggal", data.tanggalKelas)
                    .putString("instruktur", data.fInstruktur.nama)
                    .apply()
                presensi!!.edit().putInt("id", data.id)
                    .putString("jamMulai", data.jamMulai)
                    .apply()

                (activity as HomePegawaiActivity).changeFragment(SetAkhirPresensi())
            }
        })
        binding.rvPresensi.layoutManager = LinearLayoutManager(activity)
        binding.rvPresensi.adapter = adapter
    }


}