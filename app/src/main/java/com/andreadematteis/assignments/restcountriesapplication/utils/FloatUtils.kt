package com.andreadematteis.assignments.restcountriesapplication.utils

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

fun Float.toTwoDecimals(): Float = DecimalFormat("#.##").apply {
    roundingMode = RoundingMode.FLOOR
    decimalFormatSymbols = DecimalFormatSymbols().apply {
        decimalSeparator = '.'
        groupingSeparator = ' '
    }
}
    .format(this)
    .toFloat()

fun Float.toReadable(): String = DecimalFormat("###,###.##").apply {
    roundingMode = RoundingMode.FLOOR
    decimalFormatSymbols = DecimalFormatSymbols().apply {
        decimalSeparator = '.'
        groupingSeparator = ' '
    }
}.format(this)
