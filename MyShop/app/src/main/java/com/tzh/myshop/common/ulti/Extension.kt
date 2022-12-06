package com.tzh.myshop.common.ulti

import android.content.Context
import android.widget.Toast

object Extension {

    fun String.toPrice(): String {
        return this.ifEmpty { "0" }.replace(".", "").replace("-", "").trim()
    }

    fun Context.showToast(message:String){
      Toast.makeText(this, message, android.widget.Toast.LENGTH_LONG).show()
    }
}