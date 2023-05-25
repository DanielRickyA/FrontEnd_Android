package com.example.frontend_android.response.bookingGym

import com.google.gson.annotations.SerializedName

data class ResponseShowBookingGym(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class FMember(

	@field:SerializedName("deposit_uang")
	val depositUang: Int,

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("tanggal_expired")
	val tanggalExpired: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("no_telp")
	val noTelp: String,

	@field:SerializedName("tanggal_lahir")
	val tanggalLahir: String,

	@field:SerializedName("alamat")
	val alamat: String,

	@field:SerializedName("status")
	val status: String
)

data class DataItem(

	@field:SerializedName("jam_presensi")
	val jamPresensi: String?,

	@field:SerializedName("tanggal_yang_dibooking")
	val tanggalYangDibooking: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("slot_waktu")
	val slotWaktu: String,

	@field:SerializedName("f_member")
	val fMember: FMember,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String
)
