package com.ahsan.data.converters

import androidx.room.TypeConverter
import com.ahsan.data.models.Service
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ServiceListConverter {
    @TypeConverter
    fun toList(listString: String?): List<Service>? {
        val listType = object : TypeToken<List<Service?>?>() {}.type
        val list = Gson().fromJson<List<Service>>(listString, listType)
        return list
    }

    @TypeConverter
    fun fromList(list: List<Service>): String? {
        return Gson().toJson(list)
    }
}