package com.andreadematteis.assignments.restcountriesapplication.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.andreadematteis.assignments.restcountriesapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_countries)
    }
}