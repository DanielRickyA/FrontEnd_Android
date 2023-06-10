package com.example.frontend_android.response.BookingKelas

import com.google.gson.annotations.SerializedName

data class ResponseBatalKelas(

	@field:SerializedName("data")
	val data: DataKelas,

	@field:SerializedName("message")
	val message: String
)

data class DataKelas(

	@field:SerializedName("waktu_presensi")
	val waktuPresensi: Any,

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: Int,

	@field:SerializedName("tarif")
	val tarif: Any,

	@field:SerializedName("tanggal_yang_dibooking")
	val tanggalYangDibooking: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("status")
	val status: Any,

	@field:SerializedName("jenis_pembayaran")
	val jenisPembayaran: String
)
