package com.example.frontend_android.profil.profilMember

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.auth.LoginActivity
import com.example.frontend_android.databinding.FragmentProfilMemberBinding
import com.example.frontend_android.response.profilMember.ResponseProfilMember
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfilMember : Fragment() {
    private var _binding: FragmentProfilMemberBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentProfilMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref?.getString("id", " ").toString()
        getMember(id)

        binding.btnCekDepo.setOnClickListener{
            (activity as HomeMemberActivity).changeFragment(CekJumlahDeposit())
        }

        binding.btnHistoryGym.setOnClickListener{
            (activity as HomeMemberActivity).changeFragment(CekHistoryGym())
        }

        binding.btnHistoryKelas.setOnClickListener{
            (activity as HomeMemberActivity).changeFragment(CekHistoryKelas())
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

    fun getMember(id_member: String){
        val client = ApiConfig.getApiService()
        client.getMember(
            "Bearer $token",
            id_member,
        ).enqueue(object : Callback<ResponseProfilMember>{
            override fun onResponse(
                call: Call<ResponseProfilMember>,
                response: Response<ResponseProfilMember>
            ) {
                if(response.isSuccessful){
                    val responseBody  = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, "Berhasil Mendapatkan Data", Toast.LENGTH_SHORT).show()
                        binding.tvNama.text = responseBody.data.nama
                        binding.tvIdMember.text = responseBody.data.id
                        binding.tvstatus.text = responseBody.data.status
                        binding.tvDepositUang.text = responseBody.data.depositUang.toString()
                        binding.tvTanggalLahir.text = responseBody.data.tanggalLahir
                        binding.tvTanggalLahir.text = responseBody.data.tanggalLahir
                        binding.tvAlamat.text = responseBody.data.alamat
                        binding.tvPhone.text = responseBody.data.noTelp

                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseProfilMember>, t: Throwable) {
                Toast.makeText(context, "Gagal Mendapatkan Data", Toast.LENGTH_SHORT).show()
            }

        })
    }
}