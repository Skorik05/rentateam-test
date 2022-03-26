package com.rentateam.rentateam_test.model.api

class ApiHelper(private val apiService: ApiService) {
    fun getUsers(page: Int) = apiService.getUsers(page)

}