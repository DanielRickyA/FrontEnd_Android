package com.example.frontend_android.member

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentBookingKelasMemberBinding
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelasMember
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BookingKelasMemberFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentBookingKelasMemberBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var sp: SharedPreferences? = null
    var token: String? = null
    private val PEMBAYARAN = arrayOf(
        "Deposit Kelas",
        "Deposit Uang",

    )
    private var tarif = -1;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookingKelasMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        sp = requireActivity().getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        setExposedDropdownMenu()
        println(sp?.getString("tanggal", "").toString())
        println(sp?.getString("tanggal", "").toString())

        binding.tanggalJadwal.editText?.setText(sp?.getString("tanggal", "").toString())
        binding.namaInstruktur.editText?.setText(sp?.getString("instruktur", "").toString())
        binding.tvNamaKelas.editText?.setText(sp?.getString("kelas", "").toString())

        val id_member = pref?.getString("id", " ").toString()
        val id_jadwal_harian = sp?.getInt("id", -1)!!

        binding.btnSave.setOnClickListener{
            println("id_jadwal_harian: $id_jadwal_harian")
            println("id_member: $id_member")
            println("tarif: $tarif")
            bookingKelas(id_jadwal_harian, id_member, binding.edPembayaran.text.toString())
        }

        binding.btnBack.setOnClickListener {
            (activity as HomeMemberActivity).changeFragment(ShowBookingKelas())
        }
    }


    fun setExposedDropdownMenu() {
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            R.layout.item_list, PEMBAYARAN
        )
        binding.edPembayaran.setAdapter(adapter)
    }


    fun bookingKelas(id_jadwal: Int, id_member: String, jenis_pembayaran: String) {
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        val client = ApiConfig.getApiService()
        client.bookingKelas(
            "Bearer $token",
            id_jadwal,
            id_member,
            jenis_pembayaran,


        ).enqueue(object : Callback<ResponseBookingKelasMember>{
            override fun onResponse(
                call: Call<ResponseBookingKelasMember>,
                response: Response<ResponseBookingKelasMember>
            ) {
                if(response.isSuccessful){
                        val responseBody = response.body()
                        if(responseBody != null){
                            Toast.makeText(context, "Berhasil Booking Kelas", Toast.LENGTH_SHORT).show()
                            binding.loading.layoutLoading.visibility = android.view.View.GONE
                            (activity as HomeMemberActivity).changeFragment(ShowDataBookingMember())
                        }
                } else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                }
            }

            override fun onFailure(call: Call<ResponseBookingKelasMember>, t: Throwable) {
                Toast.makeText(context, "Gagal Booking Kelas", Toast.LENGTH_SHORT).show()
                binding.loading.layoutLoading.visibility = android.view.View.GONE
            }

        })
    }
}

