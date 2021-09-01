# Compose Exercise: Offline Caching
This project demonstrates the offline caching of REST API data in the local database. It fetches earthquake data from the United States Geological Survey department's server using REST API, stores it on the local database, and displays it. 

Data is filtered to show only the earthquakes of 4 magnitudes or higher, with a 400 km radius from the Bangladeshi capital Dhaka, in the past 12 months. 

Every time the app is opened, it checks for any new earthquake data on the server that is more recent than locally stored. If any new data is found, it fetches all the data, deletes everything from the database, and stores the latest data for displaying. Also sync data once in a day by running a background work using WorkManager.

## Takeaways
- Fetch data from API using the Retrofit library.
- Convert JSON data to Kotlin objects using the Moshi library.
- Cache all data from API to local database and display from there.
- Sync data once in a day by running background work using WorkManager.
- Date/Time formatting from Unix Epoch (Util.kt).
- Support `java.time` API (26+) in legacy projects (21).
- Splitting string to a list of different parts (Util.kt).
- Generating background color conditionally (Util.kt).
- Subtract 1 year from today.
- Window background (avoid showing the white screen flickering during app loading).
- Splash screen.

## Dependencies
```
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
}
```

```
compileOptions {
    // Flag to enable support for the new language APIs (java.time)
    coreLibraryDesugaringEnabled true
    ...
```

```
// Moshi
implementation "com.squareup.moshi:moshi-kotlin:1.12.0"

// Retrofit with Moshi Converter
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-moshi:2.9.0"

// Compose Coil
implementation 'io.coil-kt:coil-compose:1.3.2'

// Compose ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07"

// Compose LiveData
implementation "androidx.compose.runtime:runtime-livedata:1.0.1"

// Compose Navigation
implementation "androidx.navigation:navigation-compose:2.4.0-alpha07"

// Room
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

// Optional - Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:$room_version"

// LiveData (Flow to LiveData: asLiveData)
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"

// WorkManager - Kotlin + coroutines
implementation("androidx.work:work-runtime-ktx:2.5.0")

// WorkManager - Test helpers (Optional)
androidTestImplementation("androidx.work:work-testing:2.5.0")

// WorkManager - Multiprocess support (Optional)
implementation "androidx.work:work-multiprocess:2.5.0"

// enable support for the new language APIs (Java.Time)
coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:1.1.5'
```
<br />

|  Splash Screen | Home Screen |
| :---: | :---: |
| ![splash](https://user-images.githubusercontent.com/67064997/131287985-d44b5104-80ee-40b8-b662-690e9aaff9f0.png) | ![home](https://user-images.githubusercontent.com/67064997/131287982-22f30009-7d1b-4e13-99a6-9513f3dcee38.png) |
