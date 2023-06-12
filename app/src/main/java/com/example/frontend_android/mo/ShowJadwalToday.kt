package com.example.frontend_android.mo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.HomePegawaiActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentShowBookingKelasBinding
import com.example.frontend_android.databinding.FragmentShowJadwalTodayBinding
import com.example.frontend_android.member.BookingKelasMemberFragment
import com.example.frontend_android.member.showBookingKelasAdapter
import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import com.example.frontend_android.response.presensiInstruktur.DataItem
import com.example.frontend_android.response.presensiInstruktur.ResponseGetJadwalToday
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowJadwalToday : Fragment() {

    private var _binding: FragmentShowJadwalTodayBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentShowJadwalTodayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getJadwalHarianToday()

    }

    fun getJadwalHarianToday(){
        val client = ApiConfig.getApiService()
        client.getJadwalHarianToday("Bearer $token").enqueue(object : Callback<ResponseGetJadwalToday> {
            override fun onResponse(
                call: Call<ResponseGetJadwalToday>,
                response: Response<ResponseGetJadwalToday>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        loadRecycleView(responseBody.data as ArrayList<DataItem>)

                    }

                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseGetJadwalToday>, t: Throwable) {
                Toast.makeText(activity, "Gagal", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>){
        val adapter = ShowJadwalTodayAdapter(item)
        adapter.setCallback(object : ShowJadwalTodayAdapter.ClickInterface{
            override fun onClickItem(data: DataItem){
                Toast.makeText(activity, "Berhasil", Toast.LENGTH_SHORT).show()
                sp!!.edit().putInt("id", data.id)
                    .putString("tanggal", data.tanggalJadwalHarian)
                    .putString("instruktur", data.fInstruktur.nama)
                    .putString("kelas", data.fJadwalUmum.fKelas.nama)
                    .putString("jam", data.jamKelas)
                    .apply()

                (activity as HomePegawaiActivity).changeFragment(SetMulaiPresensiInstruktur())
            }
        })
        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvJadwalInstruktur.adapter = adapter
    }


}