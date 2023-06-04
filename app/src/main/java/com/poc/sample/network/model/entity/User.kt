package com.poc.sample.network.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
class User(
    @PrimaryKey
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("session_token")
    val token: String?,
    @SerializedName("logo_url")
    var logoUrl: String?,
    @SerializedName("register_cid")
    val registerCid: String = "",
    @SerializedName("profile_picture")
    val profilePicture: String?,
    @SerializedName("full_name")
    var fullName: String?,
    @SerializedName("phone_no")
    var phone_no: String?,
    @SerializedName("user_status")
    var status: String
)
