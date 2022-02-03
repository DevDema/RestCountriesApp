package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentDetailsLayoutBinding
import com.google.android.material.tabs.TabLayoutMediator

class DetailCountriesFragment: Fragment() {

    private lateinit var binding: FragmentDetailsLayoutBinding
    private val viewModel: DetailCountriesViewModel by activityViewModels()
    private lateinit var pagerAdapter: CountryPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentDetailsLayoutBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.pagerAdapter = CountryPagerAdapter(childFragmentManager, lifecycle)
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                CountryPagerAdapter.FRAGMENT_INFORMATION_POS -> tab.text =
                    getString(R.string.country_info_label)
                CountryPagerAdapter.FRAGMENT_COAT_POS -> tab.text =
                    getString(R.string.coat_of_arms_label)
                else -> error("Invalid fragment position in ${javaClass.simpleName}, TabLayoutMediator")
            }
        }.attach()

        viewModel.viewPagerPosition.observe(viewLifecycleOwner) {
            if(binding.tabLayout.selectedTabPosition != it) {
                binding.tabLayout.getTabAt(it)!!.select()
            }
        }
    }
}