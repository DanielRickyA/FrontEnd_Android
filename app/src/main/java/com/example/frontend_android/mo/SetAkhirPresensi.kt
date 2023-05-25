package com.example.frontend_android.mo

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontend_android.HomePegawaiActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentSetAkhirPresensiBinding
import com.example.frontend_android.databinding.FragmentSetMulaiPresensiInstrukturBinding
import com.example.frontend_android.response.presensiInstruktur.ResponseJamMulai
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetAkhirPresensi : Fragment() {
    private var _binding: FragmentSetAkhirPresensiBinding? = null
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
        _binding= FragmentSetAkhirPresensiBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        presensi = requireActivity().getSharedPreferences("presensiId", Context.MODE_PRIVATE)
        binding.tanggalJadwal.editText?.setText(sp?.getString("tanggal", "").toString())
        binding.namaInstruktur.editText?.setText(sp?.getString("instruktur", "").toString())
        binding.tvNamaKelas.editText?.setText(sp?.getString("kelas", "").toString())
        binding.tvJamKelas.editText?.setText(presensi?.getString("jamMulai", "").toString())
        binding.btnBack.setOnClickListener{
            (activity as HomePegawaiActivity).changeFragment(ShowPresensi())
        }
        binding.btnSave.setOnClickListener{
            setJamSelesai(presensi?.getInt("id", 0)!!)
        }
    }

    fun setJamSelesai(id_presensi: Int){
        val client = ApiConfig.getApiService()
        client.setJamSelesai(
            "Bearer $token",
            id_presensi,
        ).enqueue(object: Callback<ResponseJamMulai>{
            override fun onResponse(
                call: Call<ResponseJamMulai>,
                response: Response<ResponseJamMulai>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, "Berhasil Set Jam Selesai", Toast.LENGTH_SHORT).show()
                        (activity as HomePegawaiActivity).changeFragment(ShowPresensiToday())
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseJamMulai>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })

    }


}