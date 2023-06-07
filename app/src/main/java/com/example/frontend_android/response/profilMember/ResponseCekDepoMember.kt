package com.example.frontend_android.response.profilMember

import com.google.gson.annotations.SerializedName

data class ResponseCekDepoMember(

	@field:SerializedName("data")
	val data: List<DataItem>,

	@field:SerializedName("message")
	val message: String
)

data class FKelas(

	@field:SerializedName("nama")
	val nama: String,

	@field:SerializedName("harga")
	val harga: Int,

	@field:SerializedName("id")
	val id: Int
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

	@field:SerializedName("masa_berlaku_depo")
	val masaBerlakuDepo: String,

	@field:SerializedName("f_kelas")
	val fKelas: FKelas,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("f_member")
	val fMember: FMember,

	@field:SerializedName("id_kelas")
	val idKelas: Int,

	@field:SerializedName("sisa_deposit")
	val sisaDeposit: Int
)
