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
    private var flagSvg: String? = null
    private val viewModel: DetailCountriesViewModel by viewModels(
        ownerProducer = { requireParentFragment() }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        flagSvg = arguments?.getString(KEY_FLAG_SVG)
    }

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
            if(flagSvg.isNullOrEmpty()) {
                binding.coatOfArmsImage.visibility = View.INVISIBLE
                binding.coatOfArmsText.visibility = View.INVISIBLE

                binding.coatOfArmsMissing.visibility = View.VISIBLE
                return@observe
            }

            binding.coatOfArmsMissing.visibility = View.GONE
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

        binding.coatOfArmsImage.visibility = View.VISIBLE
        binding.coatOfArmsText.visibility = View.VISIBLE

        binding.coatOfArmsMissing.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()

        viewModel.setViewPagerPosition(CountryPagerAdapter.FRAGMENT_COAT_POS)
    }

    companion object {
        const val KEY_FLAG_SVG = "KEY_FLAG_SVG"
    }
}