package com.example.frontend_android.member

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
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentShowBookingKelasBinding
import com.example.frontend_android.instruktur.HistoryPerizinanFragment
import com.example.frontend_android.response.BookingKelas.DataItemBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowBookingKelas : Fragment() {
    private var _binding: FragmentShowBookingKelasBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentShowBookingKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        getJadwalHarian()
        binding.btnHistory.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ShowDataBookingMember())
        }

    }

    fun getJadwalHarian(){
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.showJadwalHarianM("Bearer $token").enqueue(object : Callback<ResponseBookingKelas> {
            override fun onResponse(
                call: Call<ResponseBookingKelas>,
                response: Response<ResponseBookingKelas>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                        loadRecycleView(responseBody.data as ArrayList<DataItemBookingKelas>)

                    }

                }
            }

            override fun onFailure(call: Call<ResponseBookingKelas>, t: Throwable) {
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItemBookingKelas>){
        val adapter = showBookingKelasAdapter(item)
        adapter.setCallback(object :showBookingKelasAdapter.ClickInterface{
            override fun onClickItem(data: DataItemBookingKelas){
                Toast.makeText(activity, "Berhasil", Toast.LENGTH_SHORT).show()
                sp!!.edit().putInt("id", data.id)
                    .putString("tanggal", data.tanggalJadwalHarian)
                    .putString("instruktur", data.fInstruktur.nama)
                    .putString("kelas", data.fJadwalUmum.fKelas.nama)
                    .apply()

                (activity as HomeMemberActivity).changeFragment(BookingKelasMemberFragment())
            }
        })
        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvJadwalInstruktur.adapter = adapter
    }

}