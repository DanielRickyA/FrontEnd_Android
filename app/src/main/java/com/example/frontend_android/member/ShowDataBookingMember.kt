package com.example.frontend_android.member

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
import com.example.frontend_android.databinding.FragmentShowDataBookingMemberBinding
import com.example.frontend_android.profil.profilMember.CekHistoryKelasAdapter
import com.example.frontend_android.profil.profilMember.ProfilMember
import com.example.frontend_android.response.BookingKelas.ResponseBatalKelas
import com.example.frontend_android.response.bookingGym.DataItem
import com.example.frontend_android.response.bookingGym.ResponseBookingGym
import com.example.frontend_android.response.profilMember.DataHistoryKelas
import com.example.frontend_android.response.profilMember.ResponseHistoryKelas
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ShowDataBookingMember : Fragment() {
    private var _binding: FragmentShowDataBookingMemberBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentShowDataBookingMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        val id = pref?.getString("id", " ").toString()
        getHistoryKelas(id)

        binding.btnBack.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ShowBookingKelas())
        }
    }

    fun getHistoryKelas(id: String){
        val client = ApiConfig.getApiService()
        client.getDataBookingKelasMember(
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
                        Toast.makeText(context, responseBody.message, Toast.LENGTH_SHORT).show()
                        loadRecycleView(responseBody.data as ArrayList<DataHistoryKelas>)
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseHistoryKelas>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataHistoryKelas>) {
        val adapter = ShowDataBookingMemberAdapter(item, object :ShowDataBookingMemberAdapter.ClickInterface{
            override fun onClickItem(data: DataHistoryKelas) {
                Toast.makeText(activity, "Berhasil Menerima Data", Toast.LENGTH_SHORT).show()
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah Membatalkan Kelas ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus"){_,_ ->
                        val client = ApiConfig.getApiService()
                        client.batalKelas(
                            "Bearer $token",
                            data.id
                        ).enqueue(object : Callback<ResponseBatalKelas>{
                            override fun onResponse(
                                call: Call<ResponseBatalKelas>,
                                response: Response<ResponseBatalKelas>
                            ) {
                                if(response.isSuccessful){
                                    val responseBody = response.body()
                                    if(responseBody != null){
                                        Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
                                        getHistoryKelas(data.idMember)
                                    }
                                }else{
                                    val errorBody = JSONObject(response.errorBody()?.string())
                                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBatalKelas>, t: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                    .show()
            }
        })
        binding.rvBookingKelas.adapter = adapter
        binding.rvBookingKelas.layoutManager = LinearLayoutManager(activity)
    }


}