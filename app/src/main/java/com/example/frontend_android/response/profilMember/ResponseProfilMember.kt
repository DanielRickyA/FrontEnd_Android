package com.example.frontend_android.response.profilMember

import com.google.gson.annotations.SerializedName

data class ResponseProfilMember(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

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
