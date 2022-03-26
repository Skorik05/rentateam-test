package com.rentateam.rentateam_test.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class UserEntity(

    @SerializedName("id")
    @Expose
    @PrimaryKey
    val id: Int,

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    val email: String,

    @SerializedName("first_name")
    @Expose
    @ColumnInfo(name = "first_name")
    val firstName: String,

    @SerializedName("last_name")
    @Expose
    @ColumnInfo(name = "last_name")
    val lastName: String,

    @SerializedName("avatar")
    @Expose
    @ColumnInfo(name = "avatar")
    val avatar: String,
) : Serializable
