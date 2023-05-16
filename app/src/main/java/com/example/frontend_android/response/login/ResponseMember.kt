package com.example.frontend_android.api

import com.google.gson.annotations.SerializedName

data class ResponseLoginMember(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("user")
    val user: UserMember? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,
)
data class UserMember(
    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("tanggal_lahir")
    val tanggal_lahir: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("no_telp")
    val no_telp: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("tanggal_expired")
    val tanggal_expired: String? = null,

    @field:SerializedName("deposit_uang")
    val deposit_uang: Int? =  null,
)