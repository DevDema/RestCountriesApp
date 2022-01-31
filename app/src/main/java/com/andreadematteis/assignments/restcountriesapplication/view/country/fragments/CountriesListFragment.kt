package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentListCountriesBinding

class CountriesListFragment: Fragment() {

    private lateinit var binding: FragmentListCountriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentListCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }
}