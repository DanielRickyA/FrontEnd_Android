package com.example.frontend_android.api

import com.example.frontend_android.response.BookingKelas.ResponseBookingKelas
import com.example.frontend_android.response.BookingKelas.ResponseBookingKelasMember
import com.example.frontend_android.response.IjinInstruktur.ReponseHistoryPerizinan
import com.example.frontend_android.response.IjinInstruktur.ResponseJadwalInstruktur
import com.example.frontend_android.response.IjinInstruktur.ResponseRequestPerizinan
import com.example.frontend_android.response.ResponseLoginInstruktur
import com.example.frontend_android.response.bookingGym.ResponseBookingGym
import com.example.frontend_android.response.bookingGym.ResponseShowBookingGym
import com.example.frontend_android.response.login.ResponseLoginPegawai
import com.example.frontend_android.response.presensiInstruktur.ResponseGetJadwalToday
import com.example.frontend_android.response.presensiInstruktur.ResponseJamMulai
import com.example.frontend_android.response.presensiInstruktur.ResponsePresensi
import com.example.frontend_android.response.promo.ResponsePromo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

object ApiConfig {

    fun getApiService() : ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.53.6.46/p3l_backend/public/api/")
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
        @Field("jenis_pembayaran") jenis_pembayaran: String,
    ): Call<ResponseBookingKelasMember>
    @GET("PromoAll")
    fun getPromo(): Call<ResponsePromo>

    @GET("JadwalHarianAll")
    fun getJadwalHarianAll(): Call<ResponseBookingKelas>

    @POST("BookingGym")
    @FormUrlEncoded
    fun bookingGym(
        @Header("Authorization") token: String,
        @Field("tanggal_yang_dibooking") tanggal_yang_dibooking: String,
        @Field("slot_waktu") slot_waktu: String,
    ): Call<ResponseBookingGym>

    @GET("BookingGym")
    fun getBookingGymMember(
        @Header("Authorization") token: String,
    ): Call<ResponseShowBookingGym>

    @DELETE("BookingGym/{id}")
    fun deleteBookingGym(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): Call<ResponseBookingGym>

    @GET("getJadwalHarianToday")
    fun getJadwalHarianToday(
        @Header("Authorization") token: String,
    ): Call<ResponseGetJadwalToday>

    @POST("PresensiInstruktur")
    @FormUrlEncoded
    fun setJamMulai(
        @Header("Authorization") token: String,
        @Field("id_jadwal_harian") id_jadwal: Int,
    ): Call<ResponseJamMulai>

    @GET("PresensiInstruktur")
    fun getPresensiToday(
        @Header("Authorization") token: String,
    ): Call<ResponsePresensi>

    @PATCH("PresensiInstruktur/{id}")
    fun setJamSelesai(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
    ): Call<ResponseJamMulai>

    @GET("PresensiInstrukturToday")
    fun getPresensiAllToday(
        @Header("Authorization") token: String,
    ): Call<ResponsePresensi>


}