package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Currency
import com.andreadematteis.assignments.restcountriesapplication.room.model.CurrencyEntity

object CurrencyConverters {

    fun toEntity(currency: Pair<String, Currency>) = CurrencyEntity(
        0,
        currency.first,
        currency.second.name,
        currency.second.symbol
    )
}