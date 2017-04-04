# localization-lib-android-basic
The basic version of the localization library for Android

## Installation

In Android Studio:

- Click file -> new -> new module
- Select *Import .JAR/.AAR Package*.
- Enter the path to the .AAR file of this library.
- Give it a name (e.g. localization-lib-android).

- In your *settings.gradle* add: `include ':localization-lib-android'`
- In your *build.gradle* add: `compile project(':localization-lib-android')`

## Usage

The localization first needs to be trained. This is done with labeled measurements. Once training is completed, location updates are sent via a callback.

The usual way to use this library is to create an instance of `Localization`, and call `startLocalization()`.

### Training

For each location, a fingerprint has to be trained.

The usual way to do this is by first calling `startFingerprint()`.
Then call `feedMeasurement()` each time you get a new measurement (like a scanned bluetooth device).
Once enough measurements are taken for the location, `finalizeFingerprint()` has to be called.

### Localizing

Once locations have a fingerprint, localization will work.

Call `track()` each time you get a new measurement (like a scanned bluetooth device).
This will trigger the callback provided in 
`startLocalization()` to be called with location updates.

### More

See the [Localization interface class](Localization.java) for all the available functions.