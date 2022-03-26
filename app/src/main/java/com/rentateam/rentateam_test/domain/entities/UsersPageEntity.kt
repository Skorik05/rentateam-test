package com.rentateam.rentateam_test.domain.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UsersPageEntity(
    @SerializedName("page")
    @Expose
    val page: Int,

    @SerializedName("per_page")
    @Expose
    val perPage: Int,

    @SerializedName("total")
    @Expose
    val total: Int,

    @SerializedName("total_pages")
    @Expose
    val totalPages: Int,

    @SerializedName("data")
    @Expose
    val data: List<UserEntity>
)