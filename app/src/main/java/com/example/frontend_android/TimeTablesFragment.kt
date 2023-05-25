package com.example.frontend_android

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentTimeTablesBinding
import com.example.frontend_android.member.BookingKelasMemberFragment
import com.example.frontend_android.member.showBookingKelasAdapter
import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import com.example.frontend_android.response.promo.DataItemPromo
import com.example.frontend_android.response.promo.ResponsePromo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TimeTablesFragment : Fragment() {
    private var _binding: FragmentTimeTablesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentTimeTablesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getJadwalHarianAll()
        getPromo()
    }
    fun getJadwalHarianAll(){
        val client = ApiConfig.getApiService()
        client.getJadwalHarianAll().enqueue(object : Callback<ResponseBookingKelas>{
            override fun onResponse(
                call: Call<ResponseBookingKelas>,
                response: Response<ResponseBookingKelas>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        loadRecycleViewJadwal(responseBody.data as ArrayList<DataItemBookingKelas>)

                    }
                }
            }

            override fun onFailure(call: Call<ResponseBookingKelas>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }

    fun getPromo(){
        val client = ApiConfig.getApiService()
        client.getPromo().enqueue(object : Callback<ResponsePromo>{
            override fun onResponse(call: Call<ResponsePromo>, response: Response<ResponsePromo>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        loadRecycleViewPromo(responseBody.data as ArrayList<DataItemPromo>)

                    }
                }
            }

            override fun onFailure(call: Call<ResponsePromo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
    fun loadRecycleViewJadwal(item: ArrayList<DataItemBookingKelas>){
        val adapter = JadwalHarianAdapter(item)
        binding.rvItemJadwal.layoutManager = LinearLayoutManager(activity)
        binding.rvItemJadwal.adapter = adapter
    }

    fun loadRecycleViewPromo(item: ArrayList<DataItemPromo>){
        val adapter = PromoAdapter(item)
        binding.rvItemPromo.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.rvItemPromo.adapter = adapter
    }

}