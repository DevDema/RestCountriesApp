package com.andreadematteis.assignments.restcountriesapplication.view.country.fragments

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.LayoutItemCountryBinding
import com.andreadematteis.assignments.restcountriesapplication.room.model.CountryEntity

class CountryListAdapter(
    private val context: Context,
    private val binder: CountryAdapterBinder,
    items: List<CountryEntity>
) :
    RecyclerView.Adapter<CountryListAdapter.ViewHolder>(), Filterable {
    class ViewHolder(val binding: LayoutItemCountryBinding) : RecyclerView.ViewHolder(binding.root)

    private val itemsBitmapsMap = mutableListOf<CountryWrapper>().apply {
        items.forEach {
            add(CountryWrapper(it, null))
        }
    }.toMutableList()

    private var filteredItemsBitmapsMap = itemsBitmapsMap.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_country,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredItemsBitmapsMap[position]

        holder.binding.item = item.countryEntity

        if (item.flag != null) {
            holder.binding.flagImage.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    android.R.color.transparent
                )
            )
            holder.binding.flagImage.setImageBitmap(item.flag)
        } else {
            holder.binding.flagImage.setImageBitmap(null)
            holder.binding.flagImage.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.black_transparent
                )
            )
        }
    }

    override fun getItemCount() = filteredItemsBitmapsMap.size

    fun setImage(bitmapPair: Pair<Long, Bitmap>) {
        itemsBitmapsMap
            .asSequence()
            .map { it.countryEntity }
            .withIndex()
            .firstOrNull { it.value.id == bitmapPair.first }
            ?.run { itemsBitmapsMap[index].flag = bitmapPair.second }

        filteredItemsBitmapsMap
            .asSequence()
            .map { it.countryEntity }
            .withIndex()
            .firstOrNull { it.value.id == bitmapPair.first }
            ?.run {
                filteredItemsBitmapsMap[index].flag = bitmapPair.second

                notifyItemChanged(index)
            }
    }

    fun filter(string: String) {
        filter.filter(string)
    }

    class FilterCountry(
        private val completeList: List<CountryWrapper>,
        private val onFiltered: (List<CountryWrapper>) -> Unit
    ) :
        Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val characterFilter = "[^\\p{L}\\p{M}\\p{N}\\p{P}\\p{Z}\\p{Cf}\\p{Cs}\\s]"
            val emojiLess = constraint.replace(Regex(characterFilter), "")

            val strings = emojiLess
                .split(",")
                .map { it.trim() }
                .filterNot { it.isBlank() }

            val filteredList = completeList.filter { countryWrapper ->
                strings.any { countryWrapper.countryEntity.name == it }
            }


            return FilterResults().apply {
                values = filteredList
                count = filteredList.size
            }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            onFiltered(results!!.values as List<CountryWrapper>)
        }

    }

    override fun getFilter() = FilterCountry(itemsBitmapsMap) { newList ->
        if(newList.isEmpty()) {
            binder.onNoCountry()
        } else {
            binder.onCountryResults(newList)
        }

        val oldList = filteredItemsBitmapsMap.toList()

        changeLists(oldList, newList)

        this.filteredItemsBitmapsMap = newList.toMutableList()
    }

    fun restoreFilter() {
        binder.onCountryResults(itemsBitmapsMap)

        val oldList = filteredItemsBitmapsMap.toList()

        changeLists(oldList, itemsBitmapsMap)

        this.filteredItemsBitmapsMap = itemsBitmapsMap
    }

    private fun changeLists(oldList: List<CountryWrapper>, newList: List<CountryWrapper>) {
        if (oldList.size > newList.size) {
            notifyItemRangeRemoved(newList.size, oldList.size)

        } else if (oldList.size < newList.size) {
            notifyItemRangeInserted(oldList.size, newList.size)

        }

        newList.zip(oldList).forEach {
            if (it.first != it.second) {
                notifyItemChanged(newList.indexOf(it.first))
            }
        }
    }


}