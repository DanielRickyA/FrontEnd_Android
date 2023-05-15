package com.example.frontend_android.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.frontend_android.R
import com.example.frontend_android.api.ApiConfig
import com.example.frontend_android.api.ResponseLoginMember
import com.example.frontend_android.databinding.ActivityLoginBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener{
            val username = binding.inputUsername.editText?.text.toString()
            val password = binding.inputPassword.editText?.text.toString()
            if(binding.RMember.isChecked){
                loginMember(username, password)
            }else if(binding.RMO.isChecked){

            }else if(binding.RInstruktur.isChecked){

            }else{
                Toast.makeText(this@LoginActivity, "Role Belom Dipilih", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun loginMember(id: String, password: String) {
        val client = ApiConfig.getApiService()

        client.loginMember(id, password).enqueue(object: Callback<ResponseLoginMember>{
            override fun onResponse(
                call: Call<ResponseLoginMember>,
                response: Response<ResponseLoginMember>
            ) {

                val responseBody = response.body()
                if(responseBody != null){
                    Toast.makeText(this@LoginActivity, "Login Berhasil" , Toast.LENGTH_SHORT).show()
                }else {
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

            }

        })
    }
}