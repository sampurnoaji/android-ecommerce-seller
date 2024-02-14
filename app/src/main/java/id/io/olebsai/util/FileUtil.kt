package id.io.olebsai.util

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

fun Uri.toFile(context: Context): File? {
    val contentResolver = context.contentResolver
    val inputStream: InputStream?

    return try {
        inputStream = contentResolver.openInputStream(this)
        val file = File(context.cacheDir, lastPathSegment ?: "temp_file")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun File.isOver2MB(): Boolean {
    // Get length of file in bytes
    val fileSizeInBytes: Long = this.length()
    // Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
    val fileSizeInKB = fileSizeInBytes / 1024
    // Convert the KB to MegaBytes (1 MB = 1024 KBytes)
    val fileSizeInMB = fileSizeInKB / 1024
    return fileSizeInMB > 2
}