package com.andreadematteis.assignments.restcountriesapplication.view.country

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.assignments.restcountriesapplication.databinding.FragmentListCountriesBinding
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CountriesListFragment : Fragment(), CountryAdapterBinder {

    private lateinit var binding: FragmentListCountriesBinding
    private val viewModel: CountriesListViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)

        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }

            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.getCountries()
    }

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


        binding.searchEditText.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        binding.searchEditText.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.setSearchText(v.text.toString())
            }

            return@setOnEditorActionListener false

        }


        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Do nothing
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    viewModel.setSearchText("")
                }
            }

        })

        viewModel.countryList.observe(viewLifecycleOwner) { list ->
            if (binding.searchEditText.adapter == null) {
                binding.searchEditText.setAdapter(
                    ArrayAdapter(
                        requireContext(),
                        R.layout.simple_list_item_1,
                        list.map {
                            "${it.flagEmoji} ${it.name}"
                        }
                    )
                )
            }

            if (binding.recyclerView.adapter == null) {
                binding.recyclerView.adapter =
                    CountryListAdapter(requireContext().applicationContext, this, list)
            }
        }

        viewModel.idImage.observe(viewLifecycleOwner) { map ->
            map.forEach {
                (binding.recyclerView.adapter as CountryListAdapter).setImage(it.toPair())
            }
        }

        viewModel.searchText.observe(viewLifecycleOwner) {
            if (it.isNullOrBlank()) {
                (binding.recyclerView.adapter as CountryListAdapter).restoreFilter()
            } else {
                (binding.recyclerView.adapter as CountryListAdapter).filter(it)
            }
        }

        binding.noCountryLabel.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
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

        val typedValue = TypedValue()

        val a: TypedArray =
            requireContext()
                .obtainStyledAttributes(typedValue.data, intArrayOf(R.attr.colorPrimaryDark))
        val color = a.getColor(0, 0)

        a.recycle()
        activity?.window?.statusBarColor = color
    }

    override fun openDetails(countryEntity: CountryEntity, bitmap: Bitmap?) {
        val action = CountriesListFragmentDirections
            .actionCountryFragmentToCountryDetailsFragment(countryEntity)
            .setFlag(bitmap)

        findNavController().navigate(action)
    }
}