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
import com.example.frontend_android.databinding.FragmentBookingKelasMemberBinding
import com.example.frontend_android.databinding.FragmentSetMulaiPresensiInstrukturBinding
import com.example.frontend_android.response.presensiInstruktur.ResponseJamMulai
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SetMulaiPresensiInstruktur : Fragment() {
    private var _binding: FragmentSetMulaiPresensiInstrukturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSetMulaiPresensiInstrukturBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        binding.tanggalJadwal.editText?.setText(sp?.getString("tanggal", "").toString())
        binding.namaInstruktur.editText?.setText(sp?.getString("instruktur", "").toString())
        binding.tvNamaKelas.editText?.setText(sp?.getString("kelas", "").toString())
        binding.tvJamKelas.editText?.setText(sp?.getString("jam", "").toString())
        val id_jadwal_harian = sp?.getInt("id", -1)!!

        binding.btnSave.setOnClickListener{
        setJamMulai(id_jadwal_harian)
        }
        binding.btnBack.setOnClickListener{
            (activity as HomePegawaiActivity).changeFragment(ShowJadwalToday())
        }

    }

    fun setJamMulai(id_jadwal: Int,){
        val client = ApiConfig.getApiService()
        client.setJamMulai(
            "Bearer $token",
                id_jadwal
        ).enqueue(object : Callback<ResponseJamMulai> {
            override fun onResponse(
                call: Call<ResponseJamMulai>,
                response: Response<ResponseJamMulai>
            ) {
                if(response.isSuccessful){
                    val reponseBody = response.body()
                    if(reponseBody != null){
                        Toast.makeText(context, "Berhasil Presensi Instruktur", Toast.LENGTH_SHORT).show()
                        (activity as HomePegawaiActivity).changeFragment(ShowPresensi())
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