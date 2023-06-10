package com.example.frontend_android.response.BookingKelas

import com.google.gson.annotations.SerializedName

data class ResponsePresensiMember(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("waktu_presensi")
	val waktuPresensi: String?,

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: Int,

	@field:SerializedName("tarif")
	val tarif: Int,

	@field:SerializedName("tanggal_yang_dibooking")
	val tanggalYangDibooking: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("jenis_pembayaran")
	val jenisPembayaran: String
)
