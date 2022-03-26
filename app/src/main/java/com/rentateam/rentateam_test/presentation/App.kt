package com.rentateam.rentateam_test.presentation

import android.app.Application
import androidx.room.Room
import com.rentateam.rentateam_test.model.db.AppDatabase
import com.rentateam.rentateam_test.model.db.DbCallback

class App: Application() {
    private var mDb: AppDatabase? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        mDb = Room
            .databaseBuilder(this, AppDatabase::class.java, "app_db")
            .addCallback(DbCallback())
            .build()
    }


    fun getDb(): AppDatabase? {
        return mDb
    }

    companion object {
        private var instance: App? = null

        fun getInstance(): App? {
            return instance
        }
    }

}