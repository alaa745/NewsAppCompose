package com.example.newsappcompose.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters {
    @TypeConverter
    fun fromStringList(value: List<String>?): String? {
    return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            Gson().fromJson(it, listType)
        } ?: emptyList()
    }

    @TypeConverter
    fun fromAny(value: Any?): String? {
        return value?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toAny(value: String?): Any? {
        return value?.let {
            val anyType = object : TypeToken<Any>() {}.type
            Gson().fromJson(it, anyType)
        }
    }
}