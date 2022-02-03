package com.andreadematteis.assignments.restcountriesapplication.utils.view

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.andreadematteis.assignments.restcountriesapplication.R
import com.andreadematteis.assignments.restcountriesapplication.databinding.LayoutItemDescriptiveBinding


class ListItemView : ConstraintLayout {

    private val binding = LayoutItemDescriptiveBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {

        if (attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.ListItemView)
            val title = a.getString(R.styleable.ListItemView_title) ?: ""
            val subtitle = a.getString(R.styleable.ListItemView_subtitle) ?: ""
            val iconId = a.getResourceId(R.styleable.ListItemView_icon_drawable, 0)
            val dividerTopVisible = a.getBoolean(R.styleable.ListItemView_divider_top, false)
            val dividerBottomVisible = a.getBoolean(R.styleable.ListItemView_divider_bottom, false)
            val clickable = a.getBoolean(R.styleable.ListItemView_is_clickable, false)

            a.recycle()

            binding.title.text = title
            binding.subtitle.text = subtitle
            binding.dividerTop.visibility = if (dividerTopVisible) View.VISIBLE else View.INVISIBLE
            binding.dividerBottom.visibility =
                if (dividerBottomVisible) View.VISIBLE else View.INVISIBLE
            iconId
                .takeUnless { it == 0 }
                ?.let {
                    binding.icon.setImageDrawable(
                        ContextCompat.getDrawable(context, it)
                    )

                }

            binding.icon.visibility = if (iconId
                    .takeUnless { it == 0 } != null
            ) View.VISIBLE
            else View.GONE

            if (clickable) {
                val outValue = TypedValue()

                getContext().theme.resolveAttribute(
                    android.R.attr.selectableItemBackground,
                    outValue,
                    true
                )

                binding.root.setBackgroundResource(outValue.resourceId)
                binding.root.isClickable = true
            }
        }
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context) : this(context, null, 0)

    fun setTitle(title: String?) {
        binding.title.text = title
    }
}