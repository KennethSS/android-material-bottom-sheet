package com.solar.bottomsheetdialog

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.marginTop
import com.google.android.material.bottomsheet.BottomSheetDialog

class SolarBottomSheet(
    private val context: Context,
    private val type: BottomSheetType = BottomSheetType.LIST,
    private val title: String = "",
    private val list: List<BottomSheetItem> = listOf(),
    private val isRippleEffect: Boolean = true,
    private val style: Int? = R.style.AppBottomSheetDialogTheme
) {

    private val dialog by lazy {
        if (style == null) BottomSheetDialog(context)
        else BottomSheetDialog(context, style)
    }

    fun show() {
        val view = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            orientation = LinearLayout.VERTICAL

            if (title.isNotEmpty()) {
                addView(generateListItemView(context, BottomSheetItem(title), false))
            }

            when(type) {
                BottomSheetType.LIST -> {
                    list.forEach { item ->
                        addView(generateListItemView(context, item, isRippleEffect))
                    }
                }
                BottomSheetType.GRID -> {
                    addView(generateGridItemView(context, list))
                }
            }
        }

        dialog.run {
            setContentView(view)
            show()
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun getDp(dp: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            dp, context.resources.displayMetrics).toInt()
    }

    private fun generateListItemView(context: Context,
                                     item: BottomSheetItem,
                                     isRippleEffect: Boolean) : LinearLayout {
        val padding = getDp(LIST_ITEM_PADDING)

        val linearLayout = LinearLayout(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                getDp(LIST_ITEM_HEIGHT)
            )
            gravity = Gravity.CENTER_VERTICAL
            setPadding(padding, padding, padding, padding)

            if (isRippleEffect) enableRippleEffect(this)
        }

        item.iconRes?.let { iconRes ->
            val size = getDp(LIST_ITEM_ICON_SIZE)
            val iv = AppCompatImageView(context).apply {
                layoutParams = ViewGroup.LayoutParams(size, size)
                setImageResource(iconRes)
            }
            linearLayout.addView(iv)
        }

        val tv = AppCompatTextView(context).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).also {
                it.marginStart = getDp(LIST_ITEM_TEXT_MARGIN)
            }
            text = item.str
        }

        linearLayout.addView(tv)
        linearLayout.setOnClickListener {
            Toast.makeText(context, item.str, Toast.LENGTH_SHORT).show()
        }

        return linearLayout
    }

    private fun generateGridItemView(context: Context, list: List<BottomSheetItem>): GridView {
        return GridView(context).apply {
            adapter = GridAdapter(list)
            numColumns = 3
            stretchMode = GridView.STRETCH_COLUMN_WIDTH
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT).apply {
                gravity = Gravity.CENTER
                setPadding(0, 0, 0, getDp(24.0f))
            }
        }
    }

    private inner class GridAdapter(private val list: List<BottomSheetItem>) : BaseAdapter() {
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
            return parent?.let {
                convertView?:getGridItemView(parent.context, list[position])
            }?:convertView
        }

        override fun getItem(position: Int): Any = list[position]
        override fun getItemId(position: Int): Long = list[position].hashCode().toLong()
        override fun getCount(): Int = list.size

        private fun getGridItemView(context: Context, item: BottomSheetItem): View {
            val ll = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).also {
                    it.gravity = Gravity.CENTER
                }

                setPadding(0, getDp(12.0f), 0, getDp(12.0f))
            }

            item.iconRes?.let {
                val icon = AppCompatImageView(context).apply {
                    setImageResource(item.iconRes)
                    layoutParams = LinearLayout.LayoutParams(
                        getDp(GRID_ITEM_IMG_SIZE),
                        getDp(GRID_ITEM_IMG_SIZE)
                    ).also {
                        it.gravity = Gravity.CENTER
                    }
                }
                ll.addView(icon)
            }

            item.str?.let {
                val tv = AppCompatTextView(context).apply {
                    text = it
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    ).also { param ->
                        param.topMargin = getDp(14.0f)
                        param.gravity = Gravity.CENTER
                    }
                }

                ll.addView(tv)
            }

            ll.setOnClickListener {
                Toast.makeText(context, item.str, Toast.LENGTH_SHORT).show()
            }

            return ll
        }
    }

    private fun enableRippleEffect(view: View) {
        val outValue = TypedValue()
        view.context.theme.resolveAttribute(
            android.R.attr.selectableItemBackground,
            outValue,
            true)

        view.setBackgroundResource(outValue.resourceId)
    }

    companion object {
        private const val LIST_ITEM_PADDING = 16.0f
        private const val LIST_ITEM_TEXT_MARGIN = 32.0f
        private const val LIST_ITEM_HEIGHT = 56.0f
        private const val LIST_ITEM_ICON_SIZE = 24.0f

        private const val GRID_ITEM_IMG_SIZE = 36.0f
    }
}