package com.example.newsappcompose

import android.app.Application
import com.example.newsappcompose.data.local.AppDatabase
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class MyApp: Application() {
    @Inject
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        initializeDatabase()
    }
    private fun initializeDatabase() {
        // Launch a coroutine to access the database
        CoroutineScope(Dispatchers.IO).launch {
            database.newsDao().getAllArticles() // Example operation
        }
    }
}