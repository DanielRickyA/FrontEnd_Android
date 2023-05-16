package com.example.frontend_android.response.IjinInstruktur

import com.google.gson.annotations.SerializedName

data class ReponseHistoryPerizinan(

	@field:SerializedName("data")
	val data: List<DataItemHistory>,

	@field:SerializedName("message")
	val message: String
)

data class DataItemHistory(

	@field:SerializedName("tanggal_buat_izin")
	val tanggalBuatIzin: String,

	@field:SerializedName("tanggal_konfirm")
	val tanggalKonfirm: Any,

	@field:SerializedName("tanggal_izin")
	val tanggalIzin: String,

	@field:SerializedName("keterangan")
	val keterangan: String,

	@field:SerializedName("f_instruktur")
	val fInstruktur: FInstruktur,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("status")
	val status: Any
)

