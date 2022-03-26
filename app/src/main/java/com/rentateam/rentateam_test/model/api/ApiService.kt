package com.rentateam.rentateam_test.model.api

import com.rentateam.rentateam_test.domain.entities.UsersPageEntity
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users/")
    fun getUsers(
        @Query(QUERY_PARAM_PAGE) page: Int = 1
    ) : Single<Response<UsersPageEntity>>

    companion object {
        private const val QUERY_PARAM_PAGE = "page"
    }
}