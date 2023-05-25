package com.example.frontend_android.member

import android.app.DatePickerDialog
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
import com.example.frontend_android.databinding.FragmentBookingGymBinding
import com.example.frontend_android.response.bookingGym.ResponseBookingGym
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class BookingGymFragment : Fragment() {
    private var _binding: FragmentBookingGymBinding? = null
    private val binding get() = _binding!!
    var pref: SharedPreferences? = null
    var token: String? = null
    private val SESI = arrayOf(
        "07:00 - 09:00",
        "09:00 - 11:00",
        "11:00 - 13:00",
        "13:00 - 15:00",
        "15:00 - 17:00",
        "17:00 - 19:00",
        "19:00 - 21:00",
    )
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding= FragmentBookingGymBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()
        setExposedDropdownMenu()
        binding.btnTanggal.setOnClickListener{
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            val dpd = DatePickerDialog(requireContext(), { view, year, monthOfYear, dayOfMonth ->
                binding.tanggalJadwal.editText?.setText(year.toString() + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", dayOfMonth))
            }, year, month, day)
            dpd.datePicker.minDate = System.currentTimeMillis()
            dpd.show()
        }
        binding.btnSave.setOnClickListener{
            bookingGym(binding.tanggalJadwal.editText?.text.toString(), binding.edSesi.text.toString())
        }

    }

    fun bookingGym(tanggal_yang_dibooking: String, slot_waktu: String){
        val client = ApiConfig.getApiService()
        client.bookingGym(
            "Bearer $token",
            tanggal_yang_dibooking,
            slot_waktu,
        ).enqueue(object : Callback<ResponseBookingGym>{
            override fun onResponse(
                call: Call<ResponseBookingGym>,
                response: Response<ResponseBookingGym>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Toast.makeText(requireContext(), "Berhasil Booking", Toast.LENGTH_SHORT).show()
                        (activity as HomeMemberActivity).changeFragment(showBookingGym())
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBookingGym>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun setExposedDropdownMenu(){
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireActivity(),
            R.layout.item_list, SESI)
        binding.edSesi.setAdapter(adapter)

    }


}