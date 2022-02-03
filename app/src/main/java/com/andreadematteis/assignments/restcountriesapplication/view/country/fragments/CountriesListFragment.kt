package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentListCountriesBinding
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.view.country.CountriesViewModel
import com.andreadematteis.assignments.restcountriesapplication.view.detailcountry.DetailCountriesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesListFragment : Fragment(), CountryAdapterBinder {

    private lateinit var binding: FragmentListCountriesBinding
    private val viewModel: CountriesListViewModel by viewModels()
    private val activityViewModel: CountriesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = FragmentListCountriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)

                val position = parent.getChildAdapterPosition(view)

                if (position == 0) {
                    outRect.top = 100
                }

                outRect.bottom = 20
                outRect.left = 40
                outRect.right = 40
            }
        })

        viewModel.countryList.observe(viewLifecycleOwner) {
            if(binding.recyclerView.adapter == null) {
                binding.recyclerView.adapter =
                    CountryListAdapter(requireContext().applicationContext, this, it)
            }

            viewModel.startWatchingImageCache()
        }

        viewModel.idImage.observe(viewLifecycleOwner) {
            (binding.recyclerView.adapter as CountryListAdapter).setImage(it)
        }

        activityViewModel.searchText.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                (binding.recyclerView.adapter as CountryListAdapter).restoreFilter()
            } else {
                (binding.recyclerView.adapter as CountryListAdapter).filter(it)
            }
        }

        binding.noCountryLabel.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE

        viewModel.getCountries()
    }

    override fun onCountryResults(countryEntities: List<CountryWrapper>) {
        binding.noCountryLabel.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onNoCountry() {
        binding.noCountryLabel.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.INVISIBLE
    }

    override fun onResume() {
        super.onResume()

        viewModel.getCountries()
    }

    override fun openDetails(countryEntity: CountryEntity, bitmap: Bitmap?) {
        startActivity(
            Intent(
                requireContext(),
                DetailCountriesActivity::class.java
            ).apply {
                putExtra(DetailCountriesActivity.ITEM_KEY_COUNTRY, countryEntity)
                putExtra(DetailCountriesActivity.ITEM_KEY_IMAGE, bitmap)
            }
        )

    }
}