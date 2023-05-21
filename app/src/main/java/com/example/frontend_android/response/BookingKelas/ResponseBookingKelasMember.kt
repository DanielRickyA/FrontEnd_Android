package com.example.frontend_android.response.BookingKelas

import com.google.gson.annotations.SerializedName

data class ResponseBookingKelasMember(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: String,

	@field:SerializedName("tarif")
	val tarif: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("id")
	val id: String,

)
