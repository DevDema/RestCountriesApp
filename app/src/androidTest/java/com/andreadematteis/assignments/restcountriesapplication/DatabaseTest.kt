package com.andreadematteis.assignments.restcountriesapplication

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Currency
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Name
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.Translation
import com.andreadematteis.assignments.restcountriesapplication.repositories.CountryRepository
import com.andreadematteis.assignments.restcountriesapplication.repositories.CurrencyRepository
import com.andreadematteis.assignments.restcountriesapplication.repositories.LanguageRepository
import com.andreadematteis.assignments.restcountriesapplication.repositories.TranslationRepository
import com.andreadematteis.assignments.restcountriesapplication.room.CountriesDatabase
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.Assert.*
import kotlin.random.Random

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DatabaseTest {

    private lateinit var applicationContext: Context
    private lateinit var database: RoomDatabase
    private lateinit var countryRepository: CountryRepository
    private lateinit var currencyRepository: CurrencyRepository
    private lateinit var languageRepository: LanguageRepository
    private lateinit var translationRepository: TranslationRepository

    @Before
    fun setup() {
        this.applicationContext = InstrumentationRegistry.getInstrumentation().context

        val database = Room.inMemoryDatabaseBuilder(
            applicationContext, CountriesDatabase::class.java
        ).build()

        this.database = database
        this.countryRepository = CountryRepository(mockk(), database)
        this.currencyRepository = CurrencyRepository(database)
        this.languageRepository = LanguageRepository(database)
        this.translationRepository = TranslationRepository(database)
    }

    @Test
    fun testSaveCountryWithEverything() {
        val testCurrencies = mapOf(
            "EUR" to Currency("Euro", "€"),
            "SEK" to Currency("Swedish crown", "$")
        )

        val testLanguages = mapOf(
            "ita" to "Italian",
            "eng" to "English",
            "swe" to "Swedish"
        )

        val testTranslations = mapOf(
            "ita" to Translation(
                "Italian",
                "Italian"
            ),
            "eng" to Translation(
                "English",
                "English"
            ),
            "swe" to Translation(
                "Swedish",
                "Swedish"
            )
        )

        val country = MockHelper.mockCountry.apply {
            currencies = testCurrencies
            languages = testLanguages
            translations = testTranslations
        }

        runBlocking {
            countryRepository.saveCountry(country)

            val countries = countryRepository.getAll()

            assertFalse(countries.isEmpty())
            assertEquals(1, countries.size)

            val currencies = currencyRepository.getAll()

            assertFalse(currencies.isEmpty())
            assertEquals(testCurrencies.size, currencies.size)

            val languages = languageRepository.getAll()

            assertFalse(currencies.isEmpty())
            assertEquals(testLanguages.size, languages.size)

            val translations = translationRepository.getAll()

            assertFalse(currencies.isEmpty())
            assertEquals(testTranslations.size, translations.size)

            val countriesComplete = countryRepository.getCompleteAll()

            assertFalse(countriesComplete.isEmpty())
            assertEquals(1, countriesComplete.size)

            assertFalse(countriesComplete[0].currencies.isEmpty())

            for (key in testCurrencies.keys) {
                assertEquals(
                    "Currencies differ at key: $key",
                    testCurrencies[key],
                    countriesComplete[0].currencies[key]
                )
            }

            for (key in testLanguages.keys) {
                assertEquals(
                    "Languages differ at key: $key",
                    testLanguages[key],
                    countriesComplete[0].languages[key]
                )
            }

            for (key in testTranslations.keys) {
                assertEquals(
                    "Translations differ at key: $key",
                    testTranslations[key],
                    countriesComplete[0].translations[key]
                )
            }
        }
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

        runBlocking {
            countryRepository.saveCountry(country)

            val countries = countryRepository.getAll()

            assertFalse(countries.isEmpty())
            assertEquals(1, countries.size)

            val currencies = currencyRepository.getAll()

            assertFalse(currencies.isEmpty())
            assertEquals(testCurrencies.size, currencies.size)

            val countriesComplete = countryRepository.getAllWithCurrency()

            assertFalse(countriesComplete.isEmpty())
            assertEquals(1, countriesComplete.size)

            assertFalse(countriesComplete[0].currencies.isEmpty())

            for (key in testCurrencies.keys) {
                assertEquals(
                    "Items differ at key: $key",
                    testCurrencies[key],
                    countriesComplete[0].currencies[key]
                )
            }
        }
    }

    @Test
    fun testSaveSameCountryMultiple() {
        val country = MockHelper.mockCountry.apply {
            name = Name("test", "" to "", "")
        }

        runBlocking {
            val savedIds = mutableListOf<Long>()

            repeat(Random(256).nextInt(2, 10)) {
                savedIds.add(countryRepository.saveCountry(country))
            }

            assertEquals(savedIds.size, savedIds.groupBy { it }.size)

            val countries = countryRepository.getAll()

            assertFalse(countries.isEmpty())
            assertEquals(1, countries.size)
        }
    }

    @Test
    fun testSaveSameLanguageMultiple() {
        val mockLanguage = "ita" to "Italian"

        runBlocking {
            val savedIds = mutableListOf<Long>()

            repeat(Random(256).nextInt(2, 10)) {
                savedIds.add(languageRepository.insert(mockLanguage))
            }

            assertEquals(2, savedIds.groupBy { it }.size)
            assertEquals(savedIds.size - 1, savedIds.count { it == -1L })
            val languages = languageRepository.getAll()

            assertFalse(languages.isEmpty())
            assertEquals(1, languages.size)
        }
    }


    @Test
    fun testSaveSameCurrencyMultiple() {
        val mockCurrency = "EUR" to Currency("Euro", "€")

        runBlocking {
            val savedIds = mutableListOf<Long>()

            repeat(Random(256).nextInt(2, 10)) {
                savedIds.add(currencyRepository.insert(mockCurrency))
            }

            assertEquals(2, savedIds.groupBy { it }.size)
            assertEquals(savedIds.size - 1, savedIds.count { it == -1L })
            val currencies = currencyRepository.getAll()

            assertFalse(currencies.isEmpty())
            assertEquals(1, currencies.size)
        }
    }

    @Test
    fun testSaveSameTranslationsMultiple() {
        val mockTranslation = "ITA" to Translation(
            "Italian",
            "Italian"
        )

        runBlocking {
            val savedIds = mutableListOf<Long>()

            repeat(Random(256).nextInt(2, 10)) {
                savedIds.add(translationRepository.insert(mockTranslation))
            }

            assertEquals(2, savedIds.groupBy { it }.size)
            assertEquals(savedIds.size - 1, savedIds.count { it == -1L })
            val translations = translationRepository.getAll()

            assertFalse(translations.isEmpty())
            assertEquals(1, translations.size)
        }
    }

    @Test
    fun testSaveCountryWithLanguages() {
        val testLanguages = mapOf(
            "ita" to "Italian",
            "eng" to "English",
            "swe" to "Swedish"
        )

        val country = MockHelper.mockCountry.apply {
            languages = testLanguages
        }

        runBlocking {
            countryRepository.saveCountry(country)

            val countries = countryRepository.getAll()

            assertFalse(countries.isEmpty())
            assertEquals(1, countries.size)

            val languages = languageRepository.getAll()

            assertFalse(languages.isEmpty())
            assertEquals(testLanguages.size, languages.size)

            val countriesComplete = countryRepository.getAllWithLanguages()

            assertFalse(countriesComplete.isEmpty())
            assertEquals(1, countriesComplete.size)

            assertFalse(countriesComplete[0].languages.isEmpty())

            for (key in testLanguages.keys) {
                assertEquals(
                    "Items differ at key: $key",
                    testLanguages[key],
                    countriesComplete[0].languages[key]
                )
            }
        }
    }

    @Test
    fun testSaveCountryWithTranslation() {
        val testTranslation = mapOf(
            "ita" to Translation(
                "Italian",
                "Italian"
            ),
            "eng" to Translation(
                "English",
                "English"
            ),
            "swe" to Translation(
                "Swedish",
                "Swedish"
            )
        )

        val country = MockHelper.mockCountry.apply {
            translations = testTranslation
        }

        runBlocking {
            countryRepository.saveCountry(country)

            val countries = countryRepository.getAll()

            assertFalse(countries.isEmpty())
            assertEquals(1, countries.size)

            val translations = translationRepository.getAll()

            assertFalse(translations.isEmpty())
            assertEquals(testTranslation.size, translations.size)

            val countriesComplete = countryRepository.getAllWithTranslations()

            assertFalse(countriesComplete.isEmpty())
            assertEquals(1, countriesComplete.size)

            assertFalse(countriesComplete[0].translations.isEmpty())

            for (key in testTranslation.keys) {
                assertEquals(
                    "Items differ at key: $key",
                    testTranslation[key],
                    countriesComplete[0].translations[key]
                )
            }
        }
    }

    @After
    fun clearDatabase() {
        database.clearAllTables()
    }
}