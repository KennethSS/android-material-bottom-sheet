package com.solar.materialbottomsheet

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.solar.library.materialbottomsheet.BottomSheetConfig
import com.solar.library.materialbottomsheet.BottomSheetItem
import com.solar.library.materialbottomsheet.BottomSheetType
import com.solar.library.materialbottomsheet.MaterialBottomSheet
import com.solar.materialbottomsheet.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val items = listOf(
            BottomSheetItem(str  = "Share", iconRes = R.drawable.ic_baseline_share_24),
            BottomSheetItem("Upload", iconRes = R.drawable.ic_baseline_cloud_upload_24),
            BottomSheetItem("Copy", iconRes = R.drawable.ic_baseline_file_copy_24),
            BottomSheetItem("Print this page", iconRes = R.drawable.ic_baseline_local_printshop_24)
        )

        binding.bottomSheetList.setOnClickListener {
            MaterialBottomSheet(this, R.style.BottomSheetThemeLight)
                .items(items)
                .setRippleEffect(true)
                .type(BottomSheetType.LIST)
                .select { index, item ->
                    when(index) {
                        0 -> {
                            // Something else
                        }
                    }
                }
                .show()
        }

        binding.bottomSheetListDark.setOnClickListener {
            MaterialBottomSheet(this, R.style.BottomSheetThemeDark)
                .title("Open in")
                .items(items)
                .setRippleEffect(true)
                .type(BottomSheetType.LIST)
                .select { index, item ->
                    when(index) {
                        0 -> {
                            // Something else
                        }
                    }
                }
                .show()
        }

        binding.bottomSheetGrid.setOnClickListener {
            MaterialBottomSheet(this)
                .title("Open in")
                .items(items)
                .setRippleEffect(true)
                .type(BottomSheetType.GRID)
                .config(BottomSheetConfig(
                    itemIconTintColor = Color.RED,
                    titleColor = Color.BLUE,
                    itemTextColor = Color.WHITE,
                    backgroundColor = Color.BLACK
                ))
                .select { index, item ->
                    Toast.makeText(this, item.str, Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}