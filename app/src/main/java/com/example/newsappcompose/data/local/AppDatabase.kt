package com.example.newsappcompose.data.local

import android.provider.MediaStore.Audio.Artists
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.newsappcompose.domain.model.Result

@Database(entities = [Result::class] , version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao
}