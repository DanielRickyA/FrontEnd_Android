package com.example.frontend_android.api

import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelasMember
import com.example.frontend_android.response.IjinInstruktur.ReponseHistoryPerizinan
import com.example.frontend_android.response.IjinInstruktur.ResponseJadwalInstruktur
import com.example.frontend_android.response.IjinInstruktur.ResponseRequestPerizinan
import com.example.frontend_android.response.ResponseLoginInstruktur
import com.example.frontend_android.response.login.ResponseLoginPegawai
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

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
    ): Call<ResponseLoginInstruktur>

    @POST("loginPegawai")
    @FormUrlEncoded
    fun loginPegawai(
        @Field("nama") nama: String,
        @Field("password") password: String,
    ): Call<ResponseLoginPegawai>

    @GET("showInstrukturHarian")
    fun getInstrukturHarian(
        @Header("Authorization") token: String,
    ): Call<ResponseJadwalInstruktur>

    @POST("PerizinanInstruktur")
    @FormUrlEncoded
    fun requestIzin(
        @Header("Authorization") token: String,
        @Field("id_jadwal") id_jadwal: Int,
        @Field("keterangan") keterangan: String,
    ): Call<ResponseRequestPerizinan>

    @GET("showIjinInsturktur")
    fun getHistoryPerizinan(
        @Header("Authorization") token: String,
    ): Call<ReponseHistoryPerizinan>

    @PUT("ChangePasswordMO")
    @FormUrlEncoded
    fun changePasswordMO(
        @Field("nama") nama: String,
        @Field("password") password: String,
    ): Call<ResponseLoginPegawai>

    @PUT("ChangePasswordInsturktur")
    @FormUrlEncoded
    fun changePasswordInstruktur(
        @Field("nama") nama: String,
        @Field("password") password: String,
    ): Call<ResponseLoginInstruktur>

    @GET("JadwalHarianM")
    fun showJadwalHarianM(
        @Header("Authorization") token: String,
    ): Call<ResponseBookingKelas>

    @POST("PresensiBookingKelas")
    @FormUrlEncoded
    fun bookingKelas(
        @Header("Authorization") token: String,
        @Field("id_jadwal_harian") id_jadwal: Int,
        @Field("id_member") id_member: String,
        @Field("tarif") tarif: Int,
    ): Call<ResponseBookingKelasMember>

}