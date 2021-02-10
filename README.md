<h1 align="center">Android Material BottomSheet</h1></br>

<p align="center">
This is the bottom sheet selection dialog for material design.

</p>

<p align="center">
  <a href="https://opensource.org/licenses/Apache-2.0"><img alt="License" src="https://img.shields.io/badge/License-Apache%202.0-blue.svg"/></a>
</p>

### Dependency Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
        maven { url "https://jitpack.io" }
    }
}
```

```gradle
dependencies {
  implementation 'com.github.KennethSS:android-material-bottom-sheet:1.1.0'
}
```


## Usage
### Basic Example
```kotlin
MaterialBottomSheet(this, R.style.BottomSheetThemeLight)
  .title("Open in") // Optional
  .items(items) 
  .setRippleEffect(true) // Default is true
  .type(BottomSheetType.LIST) // LIST, GRID
  .config(bottomSheetConfig) // Cofing Color(Optional)
  .select { index, item ->
    when(index) {
      0 -> {
        // Something else
      }
    }
  }
  .show()
```


### Config Color
```kotlin
val config = BottomSheetConfig(
              itemIconTintColor = Color.RED,
              titleColor = Color.BLUE
             ) 
```
