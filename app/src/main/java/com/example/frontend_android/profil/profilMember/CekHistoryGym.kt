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
import com.example.frontend_android.databinding.FragmentCekHistoryGymBinding
import com.example.frontend_android.member.BookingGymFragment
import com.example.frontend_android.member.showBookingGymAdapter
import com.example.frontend_android.response.bookingGym.DataItem
import com.example.frontend_android.response.bookingGym.ResponseBookingGym
import com.example.frontend_android.response.bookingGym.ResponseShowBookingGym
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CekHistoryGym : Fragment() {
    private var _binding: FragmentCekHistoryGymBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentCekHistoryGymBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        getBookingGymMember()

        binding.btnBack.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ProfilMemberNew())
        }

    }

    fun getBookingGymMember(){
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.getBookingGymMember("Bearer $token").enqueue(object :
            Callback<ResponseShowBookingGym> {
            override fun onResponse(
                call: Call<ResponseShowBookingGym>,
                response: Response<ResponseShowBookingGym>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){

                        loadRecycleView(responseBody.data as ArrayList<DataItem>)
                        binding.loading.layoutLoading.visibility = android.view.View.GONE
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                    binding.loading.layoutLoading.visibility = android.view.View.GONE

                }
            }

            override fun onFailure(call: Call<ResponseShowBookingGym>, t: Throwable) {
                Toast.makeText(activity, "Error Get Data", Toast.LENGTH_SHORT).show()
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                t.printStackTrace()
            }

        })
    }

    fun loadRecycleView(item: ArrayList<DataItem>){
        val adapter = showBookingGymAdapter(item, object : showBookingGymAdapter.ClickInterface{
            override fun onClickItem(data: DataItem) {
                Toast.makeText(activity, "Berhasil Menerima Data", Toast.LENGTH_SHORT).show()
                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                    .setMessage("Apakah anda yakin ingin menghapus mahasiswa ini?")
                    .setNegativeButton("Batal", null)
                    .setPositiveButton("Hapus"){_,_ ->
                        val client = ApiConfig.getApiService()
                        client.deleteBookingGym(
                            "Bearer $token",
                            data.id
                        ).enqueue(object : Callback<ResponseBookingGym> {
                            override fun onResponse(
                                call: Call<ResponseBookingGym>,
                                response: Response<ResponseBookingGym>
                            ) {
                                if(response.isSuccessful){
                                    val responseBody = response.body()
                                    if(responseBody != null){
                                        Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
                                        getBookingGymMember()
                                    }
                                }else{
                                    val errorBody = JSONObject(response.errorBody()?.string())
                                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                                }
                            }

                            override fun onFailure(call: Call<ResponseBookingGym>, t: Throwable) {
                                Toast.makeText(activity, "Error Hapus", Toast.LENGTH_SHORT).show()
                                t.printStackTrace()
                            }

                        })
                    }
                    .show()
            }
        })
        binding.rvBookingGym.adapter = adapter
        binding.rvBookingGym.layoutManager = LinearLayoutManager(activity)

    }

}