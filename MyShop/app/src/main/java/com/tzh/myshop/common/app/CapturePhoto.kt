package com.tzh.myshop.common.app

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.tzh.myshop.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ComposeFileProvider : FileProvider(R.xml.file_provider_paths) {
    companion object {
        fun getImageUri(context: Context): Uri {
            val timeStamp = SimpleDateFormat("MMddHHmmss", Locale.getDefault()).format(Date())
            val mFileName = "JPEG_" + timeStamp + "_"
            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

//            val directory = File(context.cacheDir, "images")
//            directory.mkdirs()
            val file = File.createTempFile(mFileName, ".jpg", storageDir)
            val authority = context.packageName + ".provider"
            return getUriForFile(
                context,
                authority,
                file,
            )
        }
    }
}

