package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.LayoutItemCountryBinding
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

class CountryListAdapter(items: List<CountryEntity>) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>() {
    class ViewHolder(val binding: LayoutItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    private val itemsBitmapsMap = buildList<Pair<CountryEntity, Bitmap?>> {
        items.forEach {
            add(it to null)
        }
    }.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_item_country,
            parent,
            false
        ))


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemsBitmapsMap[position]

        holder.binding.item = item.first
        holder.binding.flagImage.setImageBitmap(item.second)
    }

    override fun getItemCount() = itemsBitmapsMap.size

    fun setImage(bitmapPair: Pair<Long, Bitmap>) =
        itemsBitmapsMap
            .map { it.first }
            .withIndex()
            .firstOrNull { it.value.id == bitmapPair.first }
            ?.run {
                itemsBitmapsMap[index] = value to bitmapPair.second

                notifyItemChanged(index)
            }
}