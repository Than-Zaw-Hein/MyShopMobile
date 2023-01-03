package com.tzh.myshop.common.ulti

import android.content.Context
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Extension {

    fun String.toPrice(): String {
        return this.ifEmpty { "0" }.replace(".", "").replace("-", "").trim()
    }

    fun Context.showToast(message: String) {
        Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
    }

    fun Any.toJson(): String {
        return Gson().toJson(this)
    }

    fun <T> fromJson(json: String): T {
        return Gson().fromJson<T>(
            json, object : TypeToken<Any>() {}.type
        )
    }
}