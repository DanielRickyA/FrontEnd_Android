package com.example.frontend_android.response.presensiInstruktur

import com.google.gson.annotations.SerializedName

data class ResponsePresensi(

	@field:SerializedName("data")
	val data: List<DataItemPresensi>,

	@field:SerializedName("message")
	val message: String
)


data class FJadwalHarian(

	@field:SerializedName("tanggal_jadwal_harian")
	val tanggalJadwalHarian: String,

	@field:SerializedName("jam_kelas")
	val jamKelas: String,

	@field:SerializedName("last_update")
	val lastUpdate: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("id_jadwal_umum")
	val idJadwalUmum: Int,

	@field:SerializedName("status")
	val status: String
)

data class DataItemPresensi(

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: Int,

	@field:SerializedName("jam_mulai")
	val jamMulai: String,

	@field:SerializedName("jam_selesai")
	val jamSelesai: String?,

	@field:SerializedName("tanggal_kelas")
	val tanggalKelas: String,

	@field:SerializedName("durasi_kelas")
	val durasiKelas: Int,

	@field:SerializedName("f_instruktur")
	val fInstruktur: FInstruktur,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("f_jadwal_harian")
	val fJadwalHarian: FJadwalHarian,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("durasi_terlambat")
	val durasiTerlambat: Any
)
