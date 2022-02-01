package com.andreadematteis.assignments.restcountriesapplication.view.country

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.andreadematteis.assignments.restcountriesapplication.databinding.ActivityCountriesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesActivity : FragmentActivity() {

    private lateinit var binding: ActivityCountriesBinding
    private val viewModel: CountriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountriesBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.searchEditText.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        binding.searchEditText.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_DONE) {

            }

            return@setOnEditorActionListener false

        }

        viewModel.countries.observe(this) { list ->
            binding.searchEditText.setAdapter(
                ArrayAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    list.map {
                        "${it.flagEmoji} ${it.name}"
                    }
                )
            )
        }
    }
}