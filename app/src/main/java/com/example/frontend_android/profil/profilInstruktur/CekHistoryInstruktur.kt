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
import com.example.frontend_android.databinding.FragmentCekHistoryInstrukturBinding
import com.example.frontend_android.instruktur.HistoryPerizinanAdapter
import com.example.frontend_android.instruktur.ShowJadwalInstrukturFragment
import com.example.frontend_android.response.IjinInstruktur.DataItemHistory
import com.example.frontend_android.response.IjinInstruktur.ReponseHistoryPerizinan
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekHistoryInstruktur : Fragment() {
    private var _binding: FragmentCekHistoryInstrukturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCekHistoryInstrukturBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getHistoryPerizinan()

        binding.btnBack.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(ProfilInstruktur())
        }

    }

    fun getHistoryPerizinan(){
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.getHistoryPerizinan("Bearer $token").enqueue(object :
            Callback<ReponseHistoryPerizinan> {
            override fun onResponse(
                call: Call<ReponseHistoryPerizinan>,
                response: Response<ReponseHistoryPerizinan>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        loadRecycleView(responseBody.data as ArrayList<DataItemHistory>)
                        println(responseBody.data)

                        binding.loading.layoutLoading.visibility = android.view.View.GONE

                    }else{
                        val errorBody = JSONObject(response.errorBody()?.string())
                        Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                    }
                }
            }

            override fun onFailure(call: Call<ReponseHistoryPerizinan>, t: Throwable) {
                Toast.makeText(context, "Gagal Koneksi ke Server", Toast.LENGTH_SHORT).show()
                binding.loading.layoutLoading.visibility = android.view.View.GONE
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItemHistory>){
        val adapter = HistoryPerizinanAdapter(item)

        binding.rvHistoryPerizinan.layoutManager = LinearLayoutManager(activity)
        binding.rvHistoryPerizinan.adapter = adapter

    }
}