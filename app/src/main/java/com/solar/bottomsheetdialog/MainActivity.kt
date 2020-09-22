package com.solar.bottomsheetdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(
            BottomSheetItem(str  = "Share", iconRes = R.drawable.ic_baseline_share_24),
            BottomSheetItem("Upload", iconRes = R.drawable.ic_baseline_cloud_upload_24),
            BottomSheetItem("Copy", iconRes = R.drawable.ic_baseline_file_copy_24),
            BottomSheetItem("Print this page", iconRes = R.drawable.ic_baseline_local_printshop_24)
        )

        bottom_sheet_list.setOnClickListener {
            SolarBottomSheet(
                context = it.context,
                type = BottomSheetType.LIST,
                items = list,
                onSelectedItem = { dialog, position, text ->
                    dialog.dismiss()
                    Toast.makeText(dialog.context, text, Toast.LENGTH_SHORT).show()
                })
                .show()
        }

        bottom_sheet_grid.setOnClickListener {
            SolarBottomSheet(
                context = it.context,
                type = BottomSheetType.GRID,
                items = list,
                onSelectedItem = { dialog, position, text ->
                    dialog.dismiss()
                    Toast.makeText(dialog.context, text, Toast.LENGTH_SHORT).show()
                })
                .show()
        }
    }
}