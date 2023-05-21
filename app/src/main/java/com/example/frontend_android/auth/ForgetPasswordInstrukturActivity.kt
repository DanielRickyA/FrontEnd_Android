package com.example.frontend_android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.databinding.ActivityForgetPasswordInstrukturBinding
import com.example.frontend_android.databinding.ActivityForgetPasswordMoBinding
import com.example.frontend_android.response.ResponseLoginInstruktur
import com.example.frontend_android.response.login.ResponseLoginPegawai
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgetPasswordInstrukturActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetPasswordInstrukturBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordInstrukturBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener{
            changePassword(binding.inputUsername.editText?.text.toString(), binding.inputPassword.editText?.text.toString())
        }
        binding.btnBack.setOnClickListener{
            val intent = Intent(this@ForgetPasswordInstrukturActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun changePassword(nama: String, password: String){
        val client = ApiConfig.getApiService()

        client.changePasswordInstruktur(nama, password).enqueue(object : Callback<ResponseLoginInstruktur> {
            override fun onResponse(
                call: Call<ResponseLoginInstruktur>,
                response: Response<ResponseLoginInstruktur>
            ) {
                val responseBody = response.body()
                if(responseBody != null){
                    Toast.makeText(this@ForgetPasswordInstrukturActivity, "Berhasil Mengubah Password" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@ForgetPasswordInstrukturActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    if(response.code() == 404){
                        Toast.makeText(this@ForgetPasswordInstrukturActivity, "Pegawai Tidak Ditemukan" , Toast.LENGTH_SHORT).show()
                    }else if(response.code() == 400){
                        Toast.makeText(this@ForgetPasswordInstrukturActivity, "Username dan Passwrod Tidak Boleh Kosong" , Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginInstruktur>, t: Throwable) {
                Toast.makeText(this@ForgetPasswordInstrukturActivity, "Reset Password Gagal" , Toast.LENGTH_SHORT).show()
            }

        })
    }
}