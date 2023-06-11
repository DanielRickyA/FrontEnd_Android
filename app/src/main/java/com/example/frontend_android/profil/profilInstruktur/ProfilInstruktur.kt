package com.example.frontend_android.profil.profilInstruktur
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontend_android.HomeInstrukturActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.auth.LoginActivity
import com.example.frontend_android.databinding.FragmentProfilInstrukturBinding
import com.example.frontend_android.response.profilInstruktur.ResponseProfilInstruktur
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class ProfilInstruktur : Fragment() {
    private var _binding: FragmentProfilInstrukturBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentProfilInstrukturBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref!!.getInt("id", -1)
        getInstruktur(id)

        binding.btnCekHistoryPerizinan.setOnClickListener{
            (activity as HomeInstrukturActivity).changeFragment(CekHistoryInstruktur())
        }

        binding.btnCekHistoryKelas.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(CekHistoryKelasInstruktur())
        }
        binding.btnLogout.setOnClickListener{
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
            materialAlertDialogBuilder.setTitle("Konfirmasi")
                .setMessage("Apakah anda yakin keluar?")
                .setNegativeButton("Batal", null)
                .setPositiveButton("Logout"){_,_ ->
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }.show()
        }
    }

    fun getInstruktur(id: Int){
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.getInstruktur(
            "Bearer $token",
            id,
        ).enqueue(object : Callback<ResponseProfilInstruktur> {
            override fun onResponse(
                call: Call<ResponseProfilInstruktur>,
                response: Response<ResponseProfilInstruktur>
            ) {
                if(response.isSuccessful){
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    val responseBody = response.body()
                    if(responseBody != null){
                        binding.tvNama.text = responseBody.data.nama
                        binding.tvAkumulasi.text = responseBody.data.akumulasiTerlambat.toString()
                        binding.tvEmail.text = responseBody.data.email
                        binding.tvPhone.text = responseBody.data.noTelp
                        binding.tvTanggalLahir.text = responseBody.data.tanggalLahir
                        binding.tvEmail.text = responseBody.data.email
                    }
                }
            }

            override fun onFailure(call: Call<ResponseProfilInstruktur>, t: Throwable) {
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                Toast.makeText(activity, "Error", Toast.LENGTH_SHORT).show()
            }

        })
    }


}