package com.example.frontend_android.response.presensiInstruktur

import com.google.gson.annotations.SerializedName

data class ResponseJamMulai(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: String,

	@field:SerializedName("jam_mulai")
	val jamMulai: String,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String?,

	@field:SerializedName("tanggal_kelas")
	val tanggalKelas: String,

	@field:SerializedName("durasi_kelas")
	val durasiKelas: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("durasi_terlambat")
	val durasiTerlambat: Int?,
)
