package com.example.frontend_android.response.IjinInstruktur

import com.google.gson.annotations.SerializedName

data class ResponseDataInstruktur(

	@field:SerializedName("data")
	val data: List<DataInstruktur>,

	@field:SerializedName("message")
	val message: String
)

data class DataInstruktur(

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
){
	override fun toString(): String {
		return nama
	}
}
