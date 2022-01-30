package com.andreadematteis.assignments.restcountriesapplication

import android.content.Context
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Currency
import com.andreadematteis.assignments.restcountriesapplication.network.RoomModule
import com.andreadematteis.assignments.restcountriesapplication.network.repositories.CountryRepository
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@UninstallModules(RoomModule::class)
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Module
    @InstallIn(SingletonComponent::class)
    class RepositoryTestModule {

        @Singleton
        @Provides
        fun provideRoomDatabase(@ApplicationContext context: Context): CountriesDatabase =
            Room.inMemoryDatabaseBuilder(
                context, CountriesDatabase::class.java
            ).build()
    }

    @Inject
    lateinit var countryRepository: CountryRepository

    @Inject
    lateinit var database: CountriesDatabase

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testSaveCountryWithCurrency() {
        val testCurrencies = mapOf(
            "EUR" to Currency("Euro", "€"),
            "SEK" to Currency("Swedish crown", "$")
        )

        val country = MockHelper.mockCountry.apply {
            currencies = testCurrencies
        }
        countryRepository.saveCountry(country)

        val countries = database.countriesDao().getCountries()

        Assert.assertFalse(countries.isEmpty())
        Assert.assertEquals(1, countries.size)

        val currencies = database.currencyDao().getAll()

        Assert.assertFalse(currencies.isEmpty())
        Assert.assertEquals(testCurrencies.size, currencies.size)

        val countriesComplete = countryRepository.getAll()

        Assert.assertFalse(countriesComplete.isEmpty())
        Assert.assertEquals(1, countriesComplete.size)

        Assert.assertFalse(countriesComplete[0].currencies.isEmpty())
    }

    @After
    fun clearDatabase() {
        database.clearAllTables()
    }
}