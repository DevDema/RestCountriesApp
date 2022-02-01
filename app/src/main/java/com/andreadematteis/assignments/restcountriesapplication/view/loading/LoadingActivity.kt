package com.andreadematteis.assignments.restcountriesapplication.view.loading

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.ActivityLoadingBinding
import com.andreadematteis.assignments.restcountriesapplication.utils.BitmapUtils
import com.andreadematteis.assignments.restcountriesapplication.view.country.CountriesActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadingActivity: AppCompatActivity() {

    private val viewModel: LoadingViewModel by viewModels()
    private lateinit var binding: ActivityLoadingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoadingBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        binding.worldImage.setImageBitmap(BitmapUtils.bitmapFromAssets(this, "BlankMap-World.png"))
        binding.worldColoredImage.setImageBitmap(BitmapUtils.bitmapFromAssets(this, "ColoredMap-World.png"))
        viewModel.isLoadingDone.observe(this) {
            when(it) {
                LoadingStatus.IN_PROGRESS -> {
                    binding.progressCircular.visibility = View.VISIBLE
                    binding.progressText.visibility = View.VISIBLE
                }
                LoadingStatus.SUCCESS -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE

                    viewModel.stopRandomizeText()

                    binding.progressCircular.setProgress(100, true)

                    finish()

                    startActivity(Intent(this, CountriesActivity::class.java))
                }
                else -> {
                    binding.progressCircular.visibility = View.INVISIBLE
                    binding.progressText.visibility = View.INVISIBLE

                    viewModel.stopRandomizeText()

                    AlertDialog.Builder(this)
                        .setTitle(R.string.dialog_loading_error_title)
                        .setMessage(R.string.dialog_loading_error_message)
                        .setPositiveButton(R.string.start_anyway_label) { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                        .setNegativeButton(R.string.retry_label) { dialog, _ ->
                            dialog.dismiss()

                            loadCountries()
                        }.show()
                }
            }
        }

        loadCountries()
    }

    private fun loadCountries() {
        binding.progressCircular.visibility = View.VISIBLE
        binding.progressText.visibility = View.VISIBLE

        viewModel.startRandomizeText()
        viewModel.loadCountries()
    }
}