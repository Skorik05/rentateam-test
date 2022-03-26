package com.rentateam.rentateam_test.model

import android.util.Log
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.model.api.ApiHelper
import com.rentateam.rentateam_test.presentation.App
import java.lang.Exception

class Repository(private val apiHelper: ApiHelper) {
    fun getUsers(page: Int) = apiHelper.getUsers(page)
    fun getAllUsersFromDb() = App.getInstance()?.getDb()?.userDao()?.getAll()
    fun addUsersToDb(usersList: List<UserEntity>) {
        try {
            App.getInstance()?.getDb()?.userDao()?.insertUsers(*usersList.toTypedArray())
        } catch (e : Exception) {
            Log.d("InsertToDBError", "Couldn't save new instances to DB")
        }
    }
    fun deleteAllUsers() = App.getInstance()?.getDb()?.userDao()?.deleteAll()
}