package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentInformationBinding
import com.andreadematteis.assignments.restcountriesapplication.utils.BitmapUtils
import com.andreadematteis.assignments.restcountriesapplication.utils.toReadable
import com.andreadematteis.assignments.restcountriesapplication.utils.toTwoDecimals
import com.andreadematteis.assignments.restcountriesapplication.utils.view.ListItemView
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.CountryPagerAdapter
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.DetailCountriesViewModel

class CountryInformationFragment : Fragment() {

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
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        viewModel.countryEntity.observe(viewLifecycleOwner) { countryEntity ->
            binding.countryEntity = countryEntity

            binding.nativeNameItem.setTitleOrHide(countryEntity.nativeNameValue
                .takeUnless { it.isEmpty() }
                ?: countryEntity.name)
            binding.capitalItem.setTitleOrHide(countryEntity.capital.takeUnless { it.isEmpty() }
                ?.joinToString(","))
            binding.populationItem.setTitleOrHide(countryEntity.population.toReadable())
            binding.areaItem.setTitleOrHide(countryEntity.area.toReadable().toString())
            binding.continentItem.setTitleOrHide(countryEntity.continents.joinToString(", "))
            binding.timezonesItem.setTitleOrHide(countryEntity.timezones.joinToString(", "))
            binding.bordersItem.setTitleOrHide(countryEntity.borders.joinToString(", "))
            binding.carSideItem.setTitleOrHide(countryEntity.carSide.replaceFirstChar { it.uppercase() })
            binding.unLogo.setImageBitmap(
                if (countryEntity.unMember)
                    BitmapUtils.bitmapFromAssets(requireContext(), "UN_blue.png")
                else
                    null
            )

            binding.unLogoCaption.visibility =
                if (countryEntity.unMember)
                    View.VISIBLE
                else
                    View.GONE

        }
        viewModel.setViewPagerPosition(CountryPagerAdapter.FRAGMENT_INFORMATION_POS)
    }

    private fun ListItemView.setTitleOrHide(string: String?) {
        if (string.isNullOrEmpty()) {
            visibility = View.GONE
        } else {
            visibility = View.VISIBLE
            setTitle(string)
        }
    }
}