package com.andreadematteis.assignments.restcountriesapplication.network.repositories

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Currency
import com.andreadematteis.assignments.restcountriesapplication.network.services.CountriesService
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryCurrencyEntity
import com.andreadematteis.assignments.restcountriesapplication.room.utils.CurrencyConverters
import com.andreadematteis.assignments.restcountriesapplication.room.utils.toEntity
import com.andreadematteis.assignments.restcountriesapplication.room.utils.toModel

class CountryRepository(
    private val countriesService: CountriesService,
    private val roomDatabase: CountriesDatabase
) {

    fun saveCountry(country: Country) {
        val countryId = roomDatabase.countriesDao().insertCountry(country.toEntity())

        country.currencies
            .map { CurrencyConverters.toEntity(it.key to it.value) }
            .forEach {
                val currencyId = roomDatabase.currencyDao().insertCurrency(it)

                roomDatabase.countryCurrencyDao().insertCountryCurrency(
                    CountryCurrencyEntity(
                        0,
                        countryId,
                        currencyId
                    )
                )
            }

    }

    fun getAll() = roomDatabase
        .countriesDao()
        .getAll()
        .map { countryAndCurr ->
            countryAndCurr.countryEntity.toModel().apply {
                currencies = mutableMapOf<String, Currency>().apply {
                    countryAndCurr.currenciesEntity.forEach {
                        put(
                            it.identifier, Currency(
                                it.name,
                                it.symbol
                            )
                        )
                    }
                }
            }
        }.distinctBy { it.name.official }
}