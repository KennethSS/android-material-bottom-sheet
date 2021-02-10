package com.solar.library.materialbottomsheet

import android.content.Context
import androidx.annotation.StyleRes
import com.google.android.material.bottomsheet.BottomSheetDialog

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
class MaterialBottomSheet(
    private val context: Context,
    @StyleRes
    private val theme: Int? = null
) {
    private val dialog by lazy {
        if (theme != null) BottomSheetDialog(context, theme)
        else BottomSheetDialog(context)
    }

    private var items: List<BottomSheetItem> = listOf()
    private var title: String = ""
    private var isRippleEffect: Boolean = true
    private var type: BottomSheetType = BottomSheetType.LIST
    private var config: BottomSheetConfig = BottomSheetConfig()

    private var select: ((index: Int, item: BottomSheetItem) -> Unit)? = null

    fun items(items: List<BottomSheetItem>) = apply {
        this.items = items
    }

    fun title(text: String) = apply {
        this.title = text
    }

    fun setRippleEffect(enable: Boolean) = apply {
        isRippleEffect = enable
    }

    fun type(type: BottomSheetType) = apply {
        this.type = type
    }

    fun select(select: (index: Int, item: BottomSheetItem) -> Unit) = apply {
        this.select = select
    }

    fun config(config: BottomSheetConfig) = apply {
        this.config = config
    }

    fun show() {
        val view = when(type) {
            BottomSheetType.LIST -> getVerticalView()
            BottomSheetType.GRID -> getGridView()
        }

        dialog.run {
            setContentView(view)
            show()
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun getVerticalView() = run {
        VerticalBottomSheet(context).apply {
            init(items, title, config) { index, item ->
                dismiss()
                select?.invoke(index, item)
            }
        }
    }

    private fun getGridView() = run {
        GridBottomSheet(context).apply {
            init(items, title, config) { index, item ->
                dismiss()
                select?.invoke(index, item)
            }
        }
    }
}