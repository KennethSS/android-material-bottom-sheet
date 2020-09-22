<h1 align="center">Android Material Bottom Sheet</h1></br>

<p align="center">
A lightweight bottomsheetdialog like material design
</p>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).

allprojects {
    repositories {
        jcenter()
    }
}

And add a dependency code to your **module**'s `build.gradle` file.
```gradle
dependencies {
    implementation "com.github.jitpack:android-example:1.0.1"
}
```

## Usage
### Basic Example
```kotlin
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
```
