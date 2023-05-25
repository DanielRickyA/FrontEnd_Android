package com.example.frontend_android.response.bookingGym

import com.google.gson.annotations.SerializedName

data class ResponseBookingGym(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("tanggal_yang_dibooking")
	val tanggalYangDibooking: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("slot_waktu")
	val slotWaktu: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("jam_presensi")
	val jamPresensi: String?,
)
