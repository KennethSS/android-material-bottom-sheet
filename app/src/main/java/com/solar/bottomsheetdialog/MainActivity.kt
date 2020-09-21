package com.solar.bottomsheetdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv.setOnClickListener {
            val list = listOf(
                BottomSheetItem(str  = "Share", iconRes = R.drawable.ic_baseline_share_24),
                BottomSheetItem("Upload", iconRes = R.drawable.ic_baseline_cloud_upload_24),
                BottomSheetItem("Copy", iconRes = R.drawable.ic_baseline_file_copy_24),
                BottomSheetItem("Print this page", iconRes = R.drawable.ic_baseline_local_printshop_24)
            )

            SolarBottomSheet(
                context = it.context,
                type = BottomSheetType.GRID,
                list = list,
                title = "Open in")
                .show()
        }
    }
}