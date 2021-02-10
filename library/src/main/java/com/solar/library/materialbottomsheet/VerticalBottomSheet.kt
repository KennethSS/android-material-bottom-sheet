package com.solar.library.materialbottomsheet

import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

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
class VerticalBottomSheet @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = VERTICAL
        setPadding(0, dpToPx(12.0f), 0, dpToPx(12.0f))
    }

    fun init(item: List<BottomSheetItem>,
             title: String = "",
             config: BottomSheetConfig,
             onSelected: ((position: Int, item: BottomSheetItem)->Unit)?) {
        val inflater = LayoutInflater.from(context)

        if (title.isNotEmpty()) {
            val view = inflater.inflate(R.layout.item_vertical, this, false)
            view.findViewById<TextView>(R.id.item_vertical_title).run {
                text = title
                config.titleColor?.let { color ->
                    setTextColor(color)
                }
            }
            addView(view)
        }


        item.forEachIndexed { index, bottomSheetItem ->
            inflater.inflate(R.layout.item_vertical, this, false).let { view ->
                bottomSheetItem.iconRes?.let { iconRes ->
                    view.findViewById<ImageView>(R.id.item_vertical_icon).run {
                        setImageResource(iconRes)
                        config.itemIconTintColor?.let { color ->
                            setColorFilter(color, PorterDuff.Mode.SRC_IN)
                        }
                        visibility = View.VISIBLE
                    }
                }

                view.findViewById<TextView>(R.id.item_vertical_title).run {
                    text = bottomSheetItem.str
                    setTextColor(config.itemTextColor)
                }

                addView(view)

                view.setOnClickListener {
                    onSelected?.let { it(index, bottomSheetItem) }
                }
            }
        }
    }


}