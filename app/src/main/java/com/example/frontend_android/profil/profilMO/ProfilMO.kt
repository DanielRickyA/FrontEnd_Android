package com.example.frontend_android.profil.profilMO

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.auth.LoginActivity
import com.example.frontend_android.databinding.FragmentProfilMOBinding
import com.example.frontend_android.response.login.ResponseLogout
import com.example.frontend_android.response.profilInstruktur.ResponseProfilInstruktur
import com.example.frontend_android.response.profilMO.ResponseProfilMO
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilMO : Fragment() {
    private var _binding: FragmentProfilMOBinding? = null
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
        _binding = FragmentProfilMOBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        presensi = requireActivity().getSharedPreferences("presensiId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref!!.getInt("id", -1)
        getMO(id)
        binding.btnLogout.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin keluar?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Logout"){_,_ ->
                    logout()
                }.show()
        }
    }

    fun getMO(id: Int){
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.getMO(
            "Bearer $token",
            id,
        ).enqueue(object : Callback<ResponseProfilMO> {
            override fun onResponse(
                call: Call<ResponseProfilMO>,
                response: Response<ResponseProfilMO>
            ) {
                if(response.isSuccessful){
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    val responseBody = response.body()
                    if(responseBody != null){
                        binding.tvNama.text = responseBody.data.nama
                        binding.tvPhone.text = responseBody.data.noTelp
                        binding.tvTanggalLahir.text = responseBody.data.tanggalLahir
                        binding.tvAlamat.text = responseBody.data.alamat
                    }
                }
            }

            override fun onFailure(call: Call<ResponseProfilMO>, t: Throwable) {
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun logout(){
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.logoutPegawai(
            "Bearer $token",
        ).enqueue(object: Callback<ResponseLogout>{
            override fun onResponse(
                call: Call<ResponseLogout>,
                response: Response<ResponseLogout>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, "Berhasil Logout", Toast.LENGTH_SHORT).show()
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                        pref?.edit()?.clear()?.apply()
                        sp?.edit()?.clear()?.apply()
                        presensi?.edit()?.clear()?.apply()
                        val intent = Intent(activity, LoginActivity::class.java)
                        startActivity(intent)
                        activity?.finish()
                    }
                }else{
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogout>, t: Throwable) {
                Toast.makeText(context, "Gagal Logout", Toast.LENGTH_SHORT).show()
                binding.loading.layoutLoading.visibility = android.view.View.GONE
            }

        })
    }



}