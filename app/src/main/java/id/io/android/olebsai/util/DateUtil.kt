package id.io.android.olebsai.util

import java.text.SimpleDateFormat
import java.util.Locale

private val localeIndonesia = Locale("ID", "id")
val serverPattern = "yyyy-MM-dd HH:mm:ss"
val longHourPattern = "d MMMM yyyy • HH:mm"
val shortHourPattern = "d MMM yyyy • HH:mm"
val longPattern = "d MMMM yyyy"
val shortPattern = "d MMM yyyy"

fun String.toUi(outPattern: String = longHourPattern, inPattern: String = serverPattern): String {
    return try {
        var formatter = SimpleDateFormat(inPattern, localeIndonesia)
        val date = formatter.parse(this)
        formatter = SimpleDateFormat(outPattern, localeIndonesia)
        formatter.format(date)
    } catch (e: Exception) {
        this
    }
}