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
import com.example.frontend_android.databinding.FragmentCekJumlahDepositBinding
import com.example.frontend_android.member.showBookingKelasAdapter
import com.example.frontend_android.response.profilMember.DataItem

import com.example.frontend_android.response.profilMember.ResponseCekDepoMember
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CekJumlahDeposit : Fragment() {
    private var _binding: FragmentCekJumlahDepositBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCekJumlahDepositBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref?.getString("id", " ").toString()
        getDepoKelas(id)

        binding.btnBack.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ProfilMemberNew())
        }


    }

    fun getDepoKelas(id : String){
        val client = ApiConfig.getApiService()
        client.getDepoKelas(
            "Bearer $token",
            id,
        ).enqueue(object: Callback<ResponseCekDepoMember>{
            override fun onResponse(
                call: Call<ResponseCekDepoMember>,
                response: Response<ResponseCekDepoMember>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                        loadRecycleView(responseBody.data as ArrayList<DataItem>)

                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseCekDepoMember>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>) {
        val adapter = CekJumlahDepositAdapter(item)
        binding.rvDepoKelas.adapter = adapter
        binding.rvDepoKelas.layoutManager = LinearLayoutManager(activity)

    }



}