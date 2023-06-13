package com.example.frontend_android.profil.profilMember

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
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentCekHistoryKelasBinding
import com.example.frontend_android.response.profilMember.DataHistoryKelas
import com.example.frontend_android.response.profilMember.ResponseHistoryKelas
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekHistoryKelas : Fragment() {
    private var _binding: FragmentCekHistoryKelasBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCekHistoryKelasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref?.getString("id", " ").toString()
        getHistoryKelas(id)

        binding.btnBack.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ProfilMemberNew())
        }
    }

    fun getHistoryKelas(id: String){
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.getHistoryKelas(
            "Bearer $token",
            id
        ).enqueue(object : Callback<ResponseHistoryKelas> {
            override fun onResponse(
                call: Call<ResponseHistoryKelas>,
                response: Response<ResponseHistoryKelas>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){

                        loadRecycleView(responseBody.data as ArrayList<DataHistoryKelas>)
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseHistoryKelas>, t: Throwable) {
                Toast.makeText(context, "Gagal Koneksi ke Server", Toast.LENGTH_SHORT).show()
                binding.loading.layoutLoading.visibility = android.view.View.GONE
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataHistoryKelas>) {
        val adapter = CekHistoryKelasAdapter(item)
        binding.rvJadwalInstruktur.adapter = adapter
        binding.rvJadwalInstruktur.layoutManager = LinearLayoutManager(activity)
    }


}