package com.example.frontend_android.response.profilInstruktur

import com.google.gson.annotations.SerializedName

data class ResponseProfilInstruktur(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

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
