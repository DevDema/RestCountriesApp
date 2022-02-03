package com.andreadematteis.assignments.restcountriesapplication.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols


fun Int.toReadable(): String = DecimalFormat("###,###,###.##").apply {
    decimalFormatSymbols = DecimalFormatSymbols().apply {
        groupingSeparator = ' '
    }
}.format(this)
