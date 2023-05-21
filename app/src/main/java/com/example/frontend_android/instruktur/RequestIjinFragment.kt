package com.example.frontend_android.instruktur

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontend_android.HomeInstrukturActivity

import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentRequestIjinBinding

import com.example.frontend_android.response.IjinInstruktur.ResponseRequestPerizinan
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestIjinFragment : Fragment() {
    private var _binding: FragmentRequestIjinBinding? = null
    private val binding get() = _binding!!
    var sp: SharedPreferences? = null
    var pref: SharedPreferences? = null
    var token: String? = null




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestIjinBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        sp = activity?.getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()

        binding.namaInstruktur.editText?.setText(pref?.getString("nama", "").toString())
        binding.tanggalJadwal.editText?.setText(sp?.getString("tanggal", "").toString())

        binding.btnSave.setOnClickListener {
        val id_jadwal = sp?.getInt("id", -1)!!
        val keterangan = binding.keterangan.editText?.text.toString()

            requestIjin(id_jadwal, keterangan)
        }
    }

    fun requestIjin(id_jadwal: Int, keterangan:String){
        val client = ApiConfig.getApiService()
        client.requestIzin(
            "Bearer $token",
            id_jadwal,
            keterangan,
        ).enqueue(object : Callback<ResponseRequestPerizinan>{
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ResponseRequestPerizinan>,
                response: Response<ResponseRequestPerizinan>
            ) {
                val responseBody = response.body()
                    if (responseBody != null) {
                        Toast.makeText(context, "Berhasil Request Ijin", Toast.LENGTH_SHORT).show()
                        (activity as HomeInstrukturActivity).changeFragment(HistoryPerizinanFragment())
                    }else{
                        try {
                            val errorBody = JSONObject(response.errorBody()!!.string())
                            if (response.code() == 400) {
                                Toast.makeText(requireContext(), errorBody.getJSONArray("id_jadwal")[0].toString(), Toast.LENGTH_SHORT).show()
                                Toast.makeText(requireContext(), errorBody.getJSONArray("keterangan")[0].toString(), Toast.LENGTH_SHORT).show()
                            }else if (response.code() == 404){
                                Toast.makeText(requireContext(), "Jadwal tidak ditemukan", Toast.LENGTH_SHORT).show()
                            }
                        }catch (e: Exception){
                            e.printStackTrace()
                        }
                    }

            }

            override fun onFailure(call: Call<ResponseRequestPerizinan>, t: Throwable) {
                Toast.makeText(context, "Gagal Request Ijin", Toast.LENGTH_SHORT).show()
            }

        })
    }


}