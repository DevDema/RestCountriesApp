package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentInformationBinding
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.CountryPagerAdapter
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.DetailCountriesViewModel

class CountryInformationFragment: Fragment() {

    private lateinit var binding: FragmentInformationBinding
    private val viewModel: DetailCountriesViewModel by viewModels(
        ownerProducer = { requireParentFragment() }

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.setViewPagerPosition(CountryPagerAdapter.FRAGMENT_INFORMATION_POS)
    }
}