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

The localization first needs to be trained. This is done with labeled measurements.

Once training is completed, location updates are sent via a callback.

See the [Localization interface class](#Localization.java) for all the available functions.