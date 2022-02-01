package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.*
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity


fun Country.toEntity(): CountryEntity = CountryEntity(
    0,
    altSpellings ?: emptyList(),
    area,
    borders ?: emptyList(),
    capital ?: emptyList(),
    capitalInfo?.latlng ?: emptyList(),
    car?.side ?: "",
    car?.signs ?: emptyList(),
    coatOfArms?.svg ?: "",
    continents ?: emptyList(),
    demonyms?.eng?.male ?: "",
    demonyms?.eng?.female ?: "",
    fifa ?: "",
    flag ?: "",
    flags?.png ?: "",
    independent,
    landlocked,
    latlng ?: emptyList(),
    name.common,
    name.nativeName?.first ?: "",
    name.nativeName?.second ?: "",
    name.official,
    population,
    region ?: "",
    startOfWeek ?: "",
    status ?: "",
    subregion ?: "",
    timezones ?: emptyList(),
    tld ?: emptyList(),
    unMember
)

fun CountryEntity.toModel() = Country(
    id,
    altSpellings,
    area,
    borders,
    capital,
    CapitalInfo(capitalLatLng),
    Car(carSide, carSigns),
    CoatOfArms(coatOfArmsSvg),
    continents,
    Demonyms(DemonymEnglish(demonymMale, demonymFemale)),
    fifa,
    flagEmoji,
    Flags(flagsPng),
    independent,
    landlocked,
    latlng,
    Name(name, nativeNameKey to nativeNameValue, officialName),
    population,
    region,
    startOfWeek,
    status,
    subregion,
    timezones,
    tld,
    unMember
)