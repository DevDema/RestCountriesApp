package com.andreadematteis.assignments.restcountriesapplication

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.*

object MockHelper {

    val mockCountry: Country
        get() = Country(
            listOf(),
            2.0,
            listOf(),
            listOf(),
            CapitalInfo(listOf()),
            Car("", listOf()),
            CoatOfArms(""),
            listOf(),
            Demonyms(DemonymEnglish("","")),
            "",
            "",
            Flags(""),
            independent = false,
            landlocked = false,
            latlng = listOf(),
            Name("","" to "",""),
            1000,
            "",
            "",
            "",
            "",
            listOf(),
            listOf(),
            false
        )
}