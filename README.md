# Farmer Records App

A simple Android app for recording farmer crop harvests. Built with Java and XML layouts (no Kotlin, no Jetpack Compose, no extra libraries, no database).

## Features

- Add farmer records with farmer reference/name, crop, and quantity in kg
- Input validation with clear error messages
- Records displayed in a ListView
- Record count display
- State persists across screen rotation (using `onSaveInstanceState`/`onRestoreInstanceState`)
- Full lifecycle logging for debugging

## Project Structure

```
app/src/main/
├── java/com/example/famer_records/
│   ├── MainActivity.java       # Single Activity with all logic
│   ├── FarmerRecord.java       # Model class (Serializable)
│   └── FarmerRecordAdapter.java # ArrayAdapter for ListView
├── res/layout/
│   ├── activity_main.xml       # Main layout with all UI
│   └── item_record.xml         # ListView row layout
└── AndroidManifest.xml
```

## Requirements

- **Android Studio**: Ladybug (2024.2.1) or newer (uses bundled JDK)
- **Java compatibility**: 11 (source/target) — no separate JDK install needed
- **Min SDK**: 24 (Android 7.0 Nougat)
- **Target SDK**: 37
- **Compile SDK**: 37

## Building and Running

### From Android Studio
1. Open the project folder (`Famer_Records`) in Android Studio
2. Wait for Gradle sync to complete
3. Connect an Android device or start an emulator (API 24+)
4. Click **Run** ▶️ or press `Shift+F10`

### From Command Line
```bash
# Debug build
./gradlew assembleDebug

# Install to connected device/emulator
./gradlew installDebug
```

The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

## Validation Rules

| Field | Rule |
|-------|------|
| Farmer reference/name | Required, non-blank after trim |
| Crop | Required, non-blank after trim |
| Quantity | Required, numeric, finite, non-negative (zero allowed) |

On error: message shown in red TextView, fields retain user input.  
On success: record added, count updated, fields cleared.

## State Persistence

- **Rotation/config changes**: Survives via `Bundle` in `onSaveInstanceState` / `onCreate`
- **Process death / app restart**: State is **NOT** preserved (no SharedPreferences, Room, or files)

> Comment in code: *"State lives in memory and the saved-instance Bundle only. It survives rotation but NOT process death or app restart."*

## Lifecycle Logging

All lifecycle methods log to Logcat with tag `LIFECYCLE`:
- `onCreate` (logs "fresh start" or "restored")
- `onStart`
- `onResume`
- `onPause`
- `onStop`
- `onRestart`
- `onDestroy`
- `onSaveInstanceState`
- `onRestoreInstanceState`

View in Logcat: filter by tag `LIFECYCLE`.