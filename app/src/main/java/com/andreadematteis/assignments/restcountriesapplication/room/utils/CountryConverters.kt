package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.*
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity


fun Country.toEntity(): CountryEntity = CountryEntity(
    0,
    altSpellings,
    area,
    borders,
    capital,
    capitalInfo.latlng,
    car.side,
    car.signs,
    coatOfArms.svg,
    continents,
    demonyms.eng.male,
    demonyms.eng.female,
    fifa,
    flag,
    flags.svg,
    independent,
    landlocked,
    latlng,
    name.common,
    name.nativeName.first,
    name.nativeName.second,
    name.official,
    population,
    region,
    startOfWeek,
    status,
    subregion,
    timezones,
    tld,
    unMember
)

fun CountryEntity.toModel() = Country(
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
    Flags(flagsSvg),
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