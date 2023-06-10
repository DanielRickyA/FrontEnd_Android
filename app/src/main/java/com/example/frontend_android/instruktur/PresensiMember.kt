package com.example.frontend_android.instruktur

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frontend_android.HomeInstrukturActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentPresensiMemberBinding
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelasMember
import com.example.frontend_android.response.BookingKelas.ResponsePresensiMember
import com.example.frontend_android.response.presensiKelas.DataItem
import com.example.frontend_android.response.presensiKelas.DataMember
import com.example.frontend_android.response.presensiKelas.ResponseDataKelasMember
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PresensiMember : Fragment() {
    private var _binding : FragmentPresensiMemberBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding  = FragmentPresensiMemberBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        val id_jadwal_harian = sp?.getInt("id", -1)!!
        getDataMember(id_jadwal_harian)
    }

    fun getDataMember(id_jadwal_harian: Int){
        val client = ApiConfig.getApiService()
        client.getDataMember(
            "Bearer $token",
            id_jadwal_harian
        ).enqueue(object: Callback<ResponseDataKelasMember> {
            override fun onResponse(
                call: Call<ResponseDataKelasMember>,
                response: Response<ResponseDataKelasMember>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                        loadRecycleView(responseBody.data as ArrayList<DataMember>)
                    }
                }else{
                    Log.e("Error", response.errorBody()?.string().toString())
                    val errorBody = JSONObject(response.errorBody()?.string().toString())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseDataKelasMember>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataMember>){
        val adapter = PresensiMemberAdapter(item, object : PresensiMemberAdapter.ClickInterface{
            override fun onClickItem(data: DataMember) {
                Toast.makeText(context, "berhasil", Toast.LENGTH_SHORT).show()
                val client = ApiConfig.getApiService()
                client.setPresensiMember(
                    "Bearer $token",
                    data.id,
                ).enqueue(object: Callback<ResponsePresensiMember>{
                    override fun onResponse(
                        call: Call<ResponsePresensiMember>,
                        response: Response<ResponsePresensiMember>
                    ) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody != null){

                                getDataMember(data.fJadwalHarian.id)
                            }
                        }else{
                            val errorBody = JSONObject(response.errorBody()?.string().toString())
                            Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponsePresensiMember>, t: Throwable) {
                        Toast.makeText(context, "Gagal Masuk Sini", Toast.LENGTH_SHORT).show()
                    }

                })
            }

            override fun onClickBatal(data: DataMember) {
                Toast.makeText(context, "ini di batalin", Toast.LENGTH_SHORT).show()
                val client = ApiConfig.getApiService()
                client.setPresensiMemberTH(
                    "Bearer $token",
                    data.id,
                ).enqueue(object: Callback<ResponsePresensiMember>{
                    override fun onResponse(
                        call: Call<ResponsePresensiMember>,
                        response: Response<ResponsePresensiMember>
                    ) {
                        if(response.isSuccessful){
                            val responseBody = response.body()
                            if(responseBody != null){

                                getDataMember(data.fJadwalHarian.id)
                            }
                        }else{
                            val errorBody = JSONObject(response.errorBody()?.string().toString())
                            Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponsePresensiMember>, t: Throwable) {
                        Toast.makeText(context, "Gagal Masuk Sini", Toast.LENGTH_SHORT).show()
                    }

                })
            }

        })

        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
        binding.rvJadwalInstruktur.adapter = adapter
    }

}