package com.example.frontend_android.response.promo

import com.google.gson.annotations.SerializedName

data class ResponsePromo(

	@field:SerializedName("data")
	val data: List<DataItemPromo>,

	@field:SerializedName("message")
	val message: String
)

data class DataItemPromo(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("bonus")
	val bonus: Int,

	@field:SerializedName("jenis")
	val jenis: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("isi")
	val isi: String,

	@field:SerializedName("minimal_pembelian")
	val minimalPembelian: Int
)
