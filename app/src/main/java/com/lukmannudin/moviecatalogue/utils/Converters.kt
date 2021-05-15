package com.lukmannudin.moviecatalogue.utils

import java.text.DateFormatSymbols
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Lukmannudin on 15/05/21.
 */


object Converters {

    private val defaultDateFormat = "yyyy-MM-DD"

    fun String.toDate(dateFormat: String = defaultDateFormat): Date {
        val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = sdf.parse(this) ?: throw IllegalArgumentException("date format not match")
        return cal.time
    }

    fun Date.toStringFormat(): String {
        val calendar = Calendar.getInstance()
        calendar.time = this
        return "${calendar.get(Calendar.DAY_OF_MONTH)} " +
                "${getMonthForInt(calendar.get(Calendar.MONTH))}," +
                " ${calendar.get(Calendar.YEAR)}"
    }

    fun Float.toPercentage(): String {
        val decimalFormat = DecimalFormat("#")
        val percentage = this * 10
        return "${decimalFormat.format(percentage) ?: 0}%"
    }

    fun Float.toPercentageNumber(): Int {
        val decimalFormat = DecimalFormat("#")
        val percentage = this * 10
        return decimalFormat.format(percentage).toInt()
    }

    private fun getMonthForInt(num: Int): String {
        var month = "wrong"
        val dfs = DateFormatSymbols()
        val months: Array<String> = dfs.months
        if (num in 0..11) {
            month = months[num]
        }
        return month
    }


}