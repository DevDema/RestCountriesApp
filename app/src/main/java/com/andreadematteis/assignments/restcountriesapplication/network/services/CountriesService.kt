package com.andreadematteis.assignments.restcountriesapplication.network.services

import com.andreadematteis.assignments.restcountriesapplication.model.Country
import retrofit2.http.GET
import java.io.InputStream

interface CountriesService {

    @GET("all")
    suspend fun getAll(): List<Country>
}