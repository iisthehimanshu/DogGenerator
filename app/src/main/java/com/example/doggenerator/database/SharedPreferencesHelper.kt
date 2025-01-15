package com.example.doggenerator.database

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.lang.reflect.Type

class SharedPreferencesHelper(context: Context, private val prefName: String) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val gson = Gson()


    fun saveObject(key: String, obj: Any) {
        val json = gson.toJson(obj)
        sharedPreferences.edit()
            .putString(key, json)
            .apply()
    }


    fun <T> loadObject(key: String, clazz: Class<T>): T? {
        val json = sharedPreferences.getString(key, null)
        return json?.let { gson.fromJson(it, clazz) }
    }


    fun <T> loadObject(key: String, type: Type): T? {
        val json = sharedPreferences.getString(key, null)
        return json?.let { gson.fromJson(it, type) }
    }


    fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }
}
