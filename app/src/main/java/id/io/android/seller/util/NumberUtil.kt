package id.io.android.seller.util

import java.text.NumberFormat
import java.util.Locale

val indonesianLocale = Locale("id", "ID")

fun Long.addThousandSeparator(): String {
    val numberFormat = NumberFormat.getInstance(indonesianLocale)
    return numberFormat.format(this)
}