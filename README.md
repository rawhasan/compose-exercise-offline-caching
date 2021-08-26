# Compose Exercise: Offline Caching
- Fetch data from API using the Retrofit library.
- Convert JSON data to Kotlin objects using the Moshi library.
- Cache all data from API to local database and display from there.
- Date/Time formatting from Unix Epoch (Util.kt).
- Splitting string to a list of different parts (Util.kt).
- Generating background color conditionally (Util.kt).

## Dependencies
```
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-kapt'
}
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
```
