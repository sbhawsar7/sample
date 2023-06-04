package com.dev_challenge.myshopping.db.entities

import android.os.Parcelable
import androidx.lifecycle.service.R
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.poc.sample.MyApplication

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    @SerializedName("user_id")
    var userId: String,
    @SerializedName("session_token")
    var token: String,
    @SerializedName("full_name")
    var fullName: String,
    @SerializedName("phone_no")
    var phoneNo: String,
    @SerializedName("user_status")
    var userStatus: String,
    @SerializedName("profile_picture")
    var profilePicture: String,
    @SerializedName("register_cid")
    var registerCid: String,
    @SerializedName("logo_url")
    var logo_url: String
)
