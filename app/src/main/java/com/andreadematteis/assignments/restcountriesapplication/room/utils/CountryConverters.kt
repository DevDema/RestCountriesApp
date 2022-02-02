package com.andreadematteis.assignments.restcountriesapplication.room.utils

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import com.andreadematteis.assignments.restcountriesapplication.model.countryinfo.*

import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.worker.DownloadImagesForCountryWorker


fun Country.toEntity(): CountryEntity = CountryEntity(
    0,
    altSpellings ?: emptyList(),
    area,
    borders ?: emptyList(),
    capital ?: emptyList(),
    capitalInfo?.latlng ?: emptyList(),
    car?.side ?: "",
    car?.signs ?: emptyList(),
    coatOfArms?.png
        ?.replace(DownloadImagesForCountryWorker.COATS_BASE_URL, "")
        ?: "",
    continents ?: emptyList(),
    demonyms?.eng?.male ?: "",
    demonyms?.eng?.female ?: "",
    fifa ?: "",
    flag ?: "",
    flags?.png
        ?.replace(DownloadImagesForCountryWorker.FLAGS_BASE_URL, "")
        ?: "",
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
    CoatOfArms("${DownloadImagesForCountryWorker.COATS_BASE_URL}$coatOfArmsPng"),
    continents,
    Demonyms(DemonymEnglish(demonymMale, demonymFemale)),
    fifa,
    flagEmoji,
    Flags("${DownloadImagesForCountryWorker.FLAGS_BASE_URL}$flagsPng"),
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