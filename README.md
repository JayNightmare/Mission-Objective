# Mission Objective (Android / Kotlin / Compose)

A simple Material 3 app that shows "Mission Objectives" on a Google Map and lets you manage them from a modal bottom sheet. Tapping a list item opens a details screen.

## Features

- Jetpack Compose Material 3 UI
- Google Maps (Compose)
- Modal bottom sheet listing objectives with checkbox to mark complete
- Objective details screen
- Simple in-memory state via `ObjectivesViewModel`

## Setup

1. Create a Google Maps API key and enable "Maps SDK for Android" for your project: https://developers.google.com/maps/documentation/android-sdk/start
2. Open the project in Android Studio (Hedgehog or later recommended).
3. Replace the placeholder in `app/src/main/AndroidManifest.xml`:

```
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="YOUR_API_KEY_HERE" />
```

4. Run on an emulator or device with Google Play Services.

## Build & Run (optional CLI)

```bash
# From the project root
./gradlew :app:assembleDebug
./gradlew :app:installDebug
```

Note: Building from the command line requires the Android SDK/NDK set up. In most cases you'll just press Run in Android Studio.

## Notes

- Location permission is requested when you tap the My Location FAB; you can extend it to show the blue dot and follow location if desired.
- Data is in-memory for simplicity. Swap in Room or DataStore for persistence.
- The app uses Compose Material 3 components and Google Maps Compose markers for objectives.
