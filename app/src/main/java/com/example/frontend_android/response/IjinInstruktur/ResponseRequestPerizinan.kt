package com.example.frontend_android.response.IjinInstruktur

import com.google.gson.annotations.SerializedName

data class ResponseRequestPerizinan(

	@field:SerializedName("data")
	val data: Data,

	@field:SerializedName("message")
	val message: String
)

data class Data(

	@field:SerializedName("tanggal_buat_izin")
	val tanggalBuatIzin: String,

	@field:SerializedName("tanggal_konfirm")
	val tanggalKonfirm: Any,

	@field:SerializedName("tanggal_izin")
	val tanggalIzin: String,

	@field:SerializedName("instruktur_pengganti")
	val instrukturPengganti: Int,

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("status")
	val status: Any
)
