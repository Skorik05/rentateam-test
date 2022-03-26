package com.rentateam.rentateam_test.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rentateam.rentateam_test.domain.entities.UserEntity
import io.reactivex.Single

@Dao
interface UserDAO {
    @Insert
    fun insertUser(user: UserEntity)

    @Insert
    fun insertUsers(vararg users: UserEntity)

    @Query("SELECT * FROM UserEntity ORDER BY id")
    fun getAll(): Single<List<UserEntity>>

    @Query("DELETE FROM UserEntity")
    fun deleteAll()
}