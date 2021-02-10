package com.solar.library.materialbottomsheet

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

/**
 * Copyright 2020 Kenneth
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **/
class GridBottomSheet @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
    }

    fun init(
        items: List<BottomSheetItem>,
        title: String,
        config: BottomSheetConfig,
        onSelected: ((position: Int, item: BottomSheetItem) -> Unit)?
    ) {
        if (title.isNotEmpty()) {
            val tv = TextView(context).apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 16.0f)
                text = title
                setPadding(dpToPx(20.0f), dpToPx(12.0f), dpToPx(20.0f), dpToPx(12.0f))
                config.titleColor?.let { color ->
                    setTextColor(color)
                }
            }
            addView(tv)
        }

        val gridView = getGridView(context, items, title, config, onSelected)
        addView(gridView)

        config.backgroundColor?.let { bgColor ->
            setBackgroundColor(bgColor)
        }
    }

    private fun getGridView(
        context: Context,
        items: List<BottomSheetItem>,
        title: String,
        config: BottomSheetConfig,
        onSelected: ((position: Int, item: BottomSheetItem) -> Unit)?
    ): GridView {
        return GridView(context).apply {
            adapter = GridAdapter(items, config, onSelected)
            numColumns = 3
            columnWidth = dpToPx(130.0f)
            stretchMode = GridView.STRETCH_SPACING
            layoutParams = LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT
            ).apply {
                gravity = Gravity.CENTER
                setPadding(0, if (title.isEmpty()) dpToPx(20.0f) else 0, 0, dpToPx(20.0f))
            }
        }
    }

    private inner class GridAdapter(
        private val list: List<BottomSheetItem>,
        private val config: BottomSheetConfig,
        private val onSelected: ((position: Int, item: BottomSheetItem) -> Unit)?
    ) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return parent?.let {
                convertView ?: getGridItemView(position, parent.context, list[position])
            } ?: convertView
        }

        override fun getItem(position: Int): Any = list[position]
        override fun getItemId(position: Int): Long = list[position].hashCode().toLong()
        override fun getCount(): Int = list.size

        private fun getGridItemView(
            position: Int,
            context: Context,
            item: BottomSheetItem
        ): View {
            return LinearLayout(context).apply {
                orientation = VERTICAL
                layoutParams = LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT
                ).also {
                    it.gravity = Gravity.CENTER
                }

                setPadding(0, dpToPx(12.0f), 0, dpToPx(12.0f))
            }.also { linearLayout ->
                item.iconRes?.let {
                    val icon = AppCompatImageView(context).apply {
                        setImageResource(item.iconRes)
                        layoutParams = LayoutParams(
                            dpToPx(GRID_ITEM_IMG_SIZE),
                            dpToPx(GRID_ITEM_IMG_SIZE)
                        ).also {
                            it.gravity = Gravity.CENTER
                        }

                        config.itemIconTintColor?.let { color ->
                            setColorFilter(color, PorterDuff.Mode.SRC_IN)
                        }
                    }

                    linearLayout.addView(icon)
                }

                item.str.let {
                    val tv = AppCompatTextView(context).apply {
                        text = it
                        layoutParams = LayoutParams(
                            LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT
                        ).also { param ->
                            param.topMargin = dpToPx(14.0f)
                            param.gravity = Gravity.CENTER
                        }

                        setTextColor(config.itemTextColor)
                    }

                    linearLayout.addView(tv)
                }

                linearLayout.setOnClickListener {
                    onSelected?.invoke(position, item)
                }
            }
        }
    }

    companion object {
        private const val GRID_ITEM_IMG_SIZE = 36.0f
    }
}