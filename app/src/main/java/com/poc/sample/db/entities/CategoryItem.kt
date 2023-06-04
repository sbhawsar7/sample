package com.dev_challenge.myshopping.db.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")
data class CategoryItem(
    @PrimaryKey
    @SerializedName("id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("provider")
    var provider: String,
    @SerializedName("is_published")
    var isPublished: Boolean,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("modified_at")
    var modifiedAt: String,
    @SerializedName("created_by")
    var createdBy: String,
    @SerializedName("modified_by")
    var modifiedBy: String,
    @SerializedName("priority")
    var priority: Int
)
