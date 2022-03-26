package com.rentateam.rentateam_test.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rentateam.rentateam_test.domain.entities.UserEntity
import com.rentateam.rentateam_test.model.db.dao.UserDAO

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}