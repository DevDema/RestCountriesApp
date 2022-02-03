package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.coat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentCoatBinding
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.CountryPagerAdapter
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.DetailCountriesViewModel

class CoatFragment : Fragment() {

    private lateinit var binding: FragmentCoatBinding
    private val viewModel: DetailCountriesViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentCoatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.coatOfArms.observe(viewLifecycleOwner) {
            binding.coatOfArmsImage.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    if (it != null)
                        android.R.color.transparent
                    else
                        R.color.black_transparent

                )
            )

            binding.coatOfArmsImage.setImageBitmap(it)
        }

        viewModel.countryEntity.observe(viewLifecycleOwner) {
            binding.coatOfArmsText.text = getString(R.string.coat_of_arms_caption, it.name)
        }

        binding.coatOfArmsImage.setBackgroundColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.black_transparent
            )
        )
    }

    override fun onResume() {
        super.onResume()

        viewModel.setViewPagerPosition(CountryPagerAdapter.FRAGMENT_COAT_POS)
    }
}