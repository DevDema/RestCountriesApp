package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.coat.CoatFragment
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.information.CountryInformationFragment

class CountryPagerAdapter(fm: FragmentManager,
                          lifecycle: Lifecycle,
                          private val coatSvg: String) :
    FragmentStateAdapter(fm, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment =
        when (position) {
            FRAGMENT_INFORMATION_POS -> CountryInformationFragment()

            FRAGMENT_COAT_POS -> CoatFragment().apply {
                arguments = Bundle().apply {
                    putString(CoatFragment.KEY_FLAG_SVG, coatSvg)
                }
            }
            else -> error("Invalid fragment position in ${javaClass.simpleName}")
        }

    companion object {
        const val FRAGMENT_INFORMATION_POS = 0
        const val FRAGMENT_COAT_POS = 1
    }
}