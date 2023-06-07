package com.example.frontend_android.response.profilInstruktur

import com.google.gson.annotations.SerializedName

data class ResponseHistoryKelasInstruktur(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class FJadwalHarian(

	@field:SerializedName("tanggal_jadwal_harian")
	val tanggalJadwalHarian: String,

	@field:SerializedName("jam_kelas")
	val jamKelas: String,

	@field:SerializedName("f_jadwal_umum")
	val fJadwalUmum: FJadwalUmum,

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

data class DataItem(

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: Int,

	@field:SerializedName("jam_mulai")
	val jamMulai: String?,

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
	val durasiTerlambat: String?
)

data class FKelas(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("id")
	val id: Int
)

data class FInstruktur(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("akumulasi_terlambat")
	val akumulasiTerlambat: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String,

	@field:SerializedName("alamat")
	val alamat: String
)

data class FJadwalUmum(

	@field:SerializedName("f_kelas")
	val fKelas: FKelas,

	@field:SerializedName("jam_kelas")
	val jamKelas: String,

	@field:SerializedName("id_kelas")
	val idKelas: Int,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("hari_kelas")
	val hariKelas: String,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int
)
