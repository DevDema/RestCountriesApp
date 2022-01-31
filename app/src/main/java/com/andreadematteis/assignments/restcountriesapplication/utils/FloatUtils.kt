package com.andreadematteis.assignments.restcountriesapplication.utils

import java.math.RoundingMode
import java.text.DecimalFormat

fun Float.toTwoDecimals(): Float = DecimalFormat("#.##").apply {
        roundingMode = RoundingMode.FLOOR
    }
    .format(this)
    .toFloat()