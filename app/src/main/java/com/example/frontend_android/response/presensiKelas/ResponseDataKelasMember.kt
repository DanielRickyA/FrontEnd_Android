package com.example.frontend_android.response.presensiKelas

import com.google.gson.annotations.SerializedName

data class ResponseDataKelasMember(

	@field:SerializedName("data")
	val data: List<DataMember>,

	@field:SerializedName("message")
	val message: String
)

data class FJadwalHarian(

	@field:SerializedName("tanggal_jadwal_harian")
	val tanggalJadwalHarian: String,

	@field:SerializedName("jam_kelas")
	val jamKelas: String,

	@field:SerializedName("f_jadwal_umum")
	val fJadwalUmum: FJadwalUmum,

	@field:SerializedName("last_update")
	val lastUpdate: String,

	@field:SerializedName("f_instruktur")
	val fInstruktur: FInstruktur,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("id_instruktur")
	val idInstruktur: Int,

	@field:SerializedName("id_jadwal_umum")
	val idJadwalUmum: Int,

	@field:SerializedName("status")
	val status: String
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

data class DataMember(

	@field:SerializedName("waktu_presensi")
	val waktuPresensi: String,

	@field:SerializedName("id_jadwal_harian")
	val idJadwalHarian: Int,

	@field:SerializedName("tarif")
	val tarif: Int,

	@field:SerializedName("tanggal_yang_dibooking")
	val tanggalYangDibooking: String,

	@field:SerializedName("id_member")
	val idMember: String,

	@field:SerializedName("f_member")
	val fMember: FMember,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("tanggal_booking")
	val tanggalBooking: String,

	@field:SerializedName("f_jadwal_harian")
	val fJadwalHarian: FJadwalHarian,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("jenis_pembayaran")
	val jenisPembayaran: String
)
