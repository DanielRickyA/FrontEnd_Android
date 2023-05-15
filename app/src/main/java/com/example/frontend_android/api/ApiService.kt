package com.example.frontend_android.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

object ApiConfig {

    fun getApiService() : ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.18.25/p3l_backend/public/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}

interface ApiService {
    @POST("loginMember")
    @FormUrlEncoded
    fun loginMember(
        @Field("id") id: String,
        @Field("password") password: String,
    ): Call<ResponseLoginMember>

    @POST("loginInstruktur")
    @FormUrlEncoded
    fun loginInstruktur(
        @Field("nama") nama: String,
        @Field("password") password: String,
    )

}