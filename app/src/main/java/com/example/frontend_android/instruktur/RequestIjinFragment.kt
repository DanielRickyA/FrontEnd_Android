package com.example.frontend_android.instruktur

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.frontend_android.HomeInstrukturActivity

import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.FragmentRequestIjinBinding
import com.example.frontend_android.response.IjinInstruktur.DataInstruktur
import com.example.frontend_android.response.IjinInstruktur.ResponseDataInstruktur

import com.example.frontend_android.response.IjinInstruktur.ResponseRequestPerizinan
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestIjinFragment : Fragment() {
    private var _binding: FragmentRequestIjinBinding? = null
    private val binding get() = _binding!!
    var sp: SharedPreferences? = null
    var pref: SharedPreferences? = null
    var token: String? = null
    val ListInstruktur : ArrayList<DataInstruktur> = ArrayList()
    var idInstrukturPengganti = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRequestIjinBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)
        pref = activity?.getSharedPreferences("prefId", Context.MODE_PRIVATE)
        sp = activity?.getSharedPreferences("tanggalId", Context.MODE_PRIVATE)
        token = pref?.getString("token", "").toString()

        binding.namaInstruktur.editText?.setText(pref?.getString("nama", "").toString())
        binding.tanggalJadwal.editText?.setText(sp?.getString("tanggal", "").toString())
        binding.btnBack.setOnClickListener {
            (activity as HomeInstrukturActivity).changeFragment(ShowJadwalInstrukturFragment())
        }
        getDataInstruktur()
        binding.listIns.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ){
                idInstrukturPengganti = ListInstruktur[position].id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                idInstrukturPengganti = -1
            }
        }
        binding.btnSave.setOnClickListener {
            val id_jadwal = sp?.getInt("id", -1)!!
            val keterangan = binding.keterangan.editText?.text.toString()

            requestIjin(id_jadwal, keterangan, idInstrukturPengganti)
        }


    }

    fun requestIjin(id_jadwal: Int, keterangan:String, id_instruktur_pengganti: Int){
        val client = ApiConfig.getApiService()
        client.requestIzin(
            "Bearer $token",
            id_jadwal,
            keterangan,
            id_instruktur_pengganti,
        ).enqueue(object : Callback<ResponseRequestPerizinan>{
            @SuppressLint("SuspiciousIndentation")
            override fun onResponse(
                call: Call<ResponseRequestPerizinan>,
                response: Response<ResponseRequestPerizinan>
            ) {
                val responseBody = response.body()
                    if (responseBody != null) {
                        Toast.makeText(context, "Berhasil Request Ijin", Toast.LENGTH_SHORT).show()
                        (activity as HomeInstrukturActivity).changeFragment(HistoryPerizinanFragment())
                    }else{
                        val errorBody = JSONObject(response.errorBody()?.string())
                        Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                    }

            }

            override fun onFailure(call: Call<ResponseRequestPerizinan>, t: Throwable) {
                Toast.makeText(context, "Gagal Request Ijin", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getDataInstruktur(){
        val client = ApiConfig.getApiService()
        client.getInstrukturData(
            "Bearer $token",
        ).enqueue(object : Callback<ResponseDataInstruktur>{
            override fun onResponse(
                call: Call<ResponseDataInstruktur>,
                response: Response<ResponseDataInstruktur>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        ListInstruktur.addAll(responseBody.data)
                        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ListInstruktur)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.listIns.adapter = adapter
                        binding.listIns.setSelection(0)
                    }
                }else{
                    val errorBody = JSONObject(response.errorBody()?.string())
                    Toast.makeText(context, errorBody.getString("message"), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseDataInstruktur>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


}