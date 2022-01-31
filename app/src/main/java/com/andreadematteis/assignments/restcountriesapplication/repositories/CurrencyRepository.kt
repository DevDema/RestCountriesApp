package com.andreadematteis.assignments.restcountriesapplication.repositories

import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Currency
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.utils.CurrencyConverters

class CurrencyRepository(
    private val roomDatabase: CountriesDatabase
) {

    suspend fun getAll() = roomDatabase.currencyDao().getAll()

    suspend fun insert(currenciesPair: Pair<String, Currency>) = roomDatabase.currencyDao().insertCurrency(
        CurrencyConverters.toEntity(currenciesPair)
    )
}