package com.andreadematteis.assignments.restcountriesapplication.view.detailcountry

import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.ActivityCountryDetailsBinding
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity
import com.andreadematteis.assignments.restcountriesapplication.utils.ColorUtils
import com.google.android.material.tabs.TabLayoutMediator

class DetailCountriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailsBinding

    private val viewModel: DetailCountriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val countryEntity = intent.extras?.getParcelable<CountryEntity>(ITEM_KEY_COUNTRY)
            ?: error("Failed to get countryEntity from extras")

        binding = ActivityCountryDetailsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.country = countryEntity

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        viewModel.image.observe(this) {
            it?.let { bitmap ->
                setupActionBar(countryEntity, bitmap)
            }
        }

        viewModel.setCountryEntity(countryEntity)
        viewModel.setImage(
            countryEntity.id,
            intent.extras?.getParcelable(ITEM_KEY_IMAGE)
        )
        viewModel.loadImage(countryEntity.id)

        supportFragmentManager.beginTransaction()
            .replace(binding.content.id, DetailCountriesFragment())
            .commit()
    }

    private fun setupActionBar(countryEntity: CountryEntity, bitmap: Bitmap) {
        val colorPrimary: TypedArray =
            obtainStyledAttributes(
                TypedValue().data,
                intArrayOf(
                    androidx.appcompat.R.attr.colorPrimary,
                    androidx.appcompat.R.attr.colorPrimaryDark
                )
            )

        val palette = Palette.Builder(bitmap)
            .generate()

        val color = palette.mutedSwatch?.rgb
            ?: palette.darkMutedSwatch?.rgb
            ?: palette.darkVibrantSwatch?.rgb
            ?: palette.vibrantSwatch?.rgb
            ?: colorPrimary.getColor(0, 0)
        val colorDark = ColorUtils.manipulateColor(color, 0.7F)

        colorPrimary.recycle()

        window.statusBarColor = colorDark

        binding.collapsingToolbar.setContentScrimColor(color)
        binding.headerImage.setImageBitmap(bitmap)

        if (KNOWN_BAD_CONTRAST_FLAGS.any { it == countryEntity.name }) {
            binding.collapsingToolbar.setExpandedTitleTextColor(ColorStateList.valueOf(Color.BLACK))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ITEM_KEY_IMAGE = "ITEM_KEY_IMAGE"
        const val ITEM_KEY_COUNTRY = "ITEM_KEY_COUNTRY"

        val KNOWN_BAD_CONTRAST_FLAGS = arrayOf("Afghanistan")
    }
}