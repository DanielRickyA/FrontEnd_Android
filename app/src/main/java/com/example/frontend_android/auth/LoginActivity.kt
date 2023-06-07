package com.example.frontend_android.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frontend_android.HomeInstrukturActivity
import com.example.frontend_android.HomeMemberActivity
import com.example.frontend_android.HomePegawaiActivity
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.api.ResponseLoginMember
import com.example.frontend_android.databinding.ActivityLoginBinding
import com.example.frontend_android.response.ResponseLoginInstruktur
import com.example.frontend_android.response.login.ResponseLoginPegawai
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    var pref: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pref = getSharedPreferences("prefId", Context.MODE_PRIVATE)

        binding.btnLogin.setOnClickListener{
            val username = binding.inputUsername.editText?.text.toString()
            val password = binding.inputPassword.editText?.text.toString()
            if(binding.RMember.isChecked){
                loginMember(username, password)
            }else if(binding.RMO.isChecked){
                loginPegawai(username, password)
            }else if(binding.RInstruktur.isChecked){
                loginInstruktur(username, password)
            }else{
                Toast.makeText(this@LoginActivity, "Role Belom Dipilih", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnForget.setOnClickListener{
            if(binding.RMember.isChecked){
                Toast.makeText(this@LoginActivity, "Member Tidak Bisa Mengubah Password", Toast.LENGTH_SHORT).show()
            }else if(binding.RMO.isChecked){
                val intent = Intent(this@LoginActivity, ForgetPasswordMOActivity::class.java)
                startActivity(intent)
                finish()
            }else if(binding.RInstruktur.isChecked) {
                val intent =
                    Intent(this@LoginActivity, ForgetPasswordInstrukturActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this@LoginActivity, "Role Belom Dipilih", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun loginMember(id: String, password: String) {
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.loginMember(id, password).enqueue(object: Callback<ResponseLoginMember>{
            override fun onResponse(
                call: Call<ResponseLoginMember>,
                response: Response<ResponseLoginMember>
            ) {

                val responseBody = response.body()
                if(responseBody != null){
                    Toast.makeText(this@LoginActivity, "Login Berhasil" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, HomeMemberActivity::class.java)
                    pref!!.edit().putString("token", responseBody.accessToken).apply()
                    pref!!.edit().putString("id", responseBody.user?.id).apply()
                    pref!!.edit().putString("nama", responseBody.user?.nama).apply()
                    startActivity(intent)
                    finish()
                }else {
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    try {
                        val errorBody = JSONObject(response.errorBody()!!.string())
                        if (response.code() == 401) {
                            Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        } else if (response.code() == 500) {
                            Toast.makeText(this@LoginActivity, "Server Down", Toast.LENGTH_SHORT).show()
                        }else if (response.code() == 400){
                            if(errorBody.has("id")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("id")[0].toString(), Toast.LENGTH_SHORT).show()
                            }
                            else if(errorBody.has("password")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("password")[0].toString(), Toast.LENGTH_SHORT).show()
                            }

                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginMember>, t: Throwable) {
                binding.loading.layoutLoading.visibility = android.view.View.GONE
                Toast.makeText(this@LoginActivity, "Internet Mati", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loginInstruktur(nama: String, password: String){
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.loginInstruktur(nama, password).enqueue(object: Callback<ResponseLoginInstruktur>{
            override fun onResponse(
                call: Call<ResponseLoginInstruktur>,
                response: Response<ResponseLoginInstruktur>
            ) {
                val responseBody = response.body()
                if(responseBody != null){
                    Toast.makeText(this@LoginActivity, "Login Berhasil" , Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, HomeInstrukturActivity::class.java)
                    pref!!.edit().putString("token", responseBody.accessToken).apply()
                    pref!!.edit().putInt("id", responseBody.user.id).apply()
                    pref!!.edit().putString("nama", responseBody.user.nama).apply()
                    startActivity(intent)
                    finish()
                }else {
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    try {
                        val errorBody = JSONObject(response.errorBody()!!.string())
                        if (response.code() == 401) {
                            Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        } else if (response.code() == 500) {
                            Toast.makeText(this@LoginActivity, "Server Down", Toast.LENGTH_SHORT).show()
                        }else if (response.code() == 400){
                            if(errorBody.has("id")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("nama")[0].toString(), Toast.LENGTH_SHORT).show()
                            }
                            else if(errorBody.has("password")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("password")[0].toString(), Toast.LENGTH_SHORT).show()
                            }

                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginInstruktur>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Internet Mati", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun loginPegawai(nama: String, password: String){
        val client = ApiConfig.getApiService()
        binding.loading.layoutLoading.visibility = android.view.View.VISIBLE
        client.loginPegawai(nama, password).enqueue(object: Callback<ResponseLoginPegawai>{
            override fun onResponse(
                call: Call<ResponseLoginPegawai>,
                response: Response<ResponseLoginPegawai>
            ) {
                val responseBody = response.body()
                if(responseBody != null){
                    Toast.makeText(this@LoginActivity, "Login Berhasil" , Toast.LENGTH_SHORT).show()
                    pref!!.edit().putString("token", responseBody.accessToken).apply()
//                    pref!!.edit().putString("id", responseBody.user.id).apply()
                    val intent = Intent(this@LoginActivity, HomePegawaiActivity::class.java)
                    startActivity(intent)
                }else {
                    binding.loading.layoutLoading.visibility = android.view.View.GONE
                    try {
                        val errorBody = JSONObject(response.errorBody()!!.string())
                        if (response.code() == 401) {
                            Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        } else if (response.code() == 500) {
                            Toast.makeText(this@LoginActivity, "Server Down", Toast.LENGTH_SHORT).show()
                        }else if (response.code() == 400){
                            if(errorBody.has("id")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("nama")[0].toString(), Toast.LENGTH_SHORT).show()
                            }
                            else if(errorBody.has("password")){
                                Toast.makeText(this@LoginActivity, errorBody.getJSONArray("password")[0].toString(), Toast.LENGTH_SHORT).show()
                            }

                        }
                    }catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<ResponseLoginPegawai>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Internet Mati", Toast.LENGTH_SHORT).show()
            }
        })
    }


}