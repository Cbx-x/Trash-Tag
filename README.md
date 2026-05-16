# Paryavaran Kavalu (Eco-Guardian App)

**Paryavaran Kavalu** is a modern Android application designed to empower citizens to contribute to a cleaner environment. By bridging the gap between concerned citizens (Reporters) and waste management personnel (Cleaners), the app facilitates efficient waste reporting and tracking.

## 🌟 Features

### For Reporters
*   **Easy Reporting**: Report waste issues like "Garbage on Road" or "Overflowing Bin" with just a few taps.
*   **Photo Capture**: Take photos of the waste site directly within the app.
*   **Smart Location**: Automatically detects location using GPS or allows manual entry.
*   **Rewards System**: Earn points for every valid report submitted, encouraging consistent participation.
*   **Track Progress**: View your submission history and rewards dashboard.

### For Cleaners
*   **Assigned Tasks**: View reports assigned for cleanup.
*   **Map Integration**: Visualize waste reports on a map for better route planning and identification.
*   **Work History**: Keep track of resolved reports and professional activity.

## 🛠️ Tech Stack

*   **Language**: Kotlin
*   **UI Framework**: Jetpack Compose (Modern Declarative UI)
*   **Backend**: Firebase
    *   **Authentication**: Secure login for Reporters and Cleaners.
    *   **Firestore**: Real-time database for reports, user profiles, and rewards.
*   **Maps & Location**: 
    *   Google Maps SDK for Compose
    *   Google Play Services Location for precise geotagging.
*   **Architecture**: MVVM (Model-View-ViewModel)

## 📸 Screenshots

*(Add your screenshots here to showcase the app's beautiful Material 3 design)*

## 🚀 Getting Started

1.  **Clone the repository**
2.  **Firebase Setup**:
    *   Create a project in the [Firebase Console](https://console.firebase.google.com/).
    *   Add an Android app with the package name `com.mindmatrix.app1`.
    *   Download `google-services.json` and place it in the `app/` directory.
    *   Enable Email/Password authentication.
    *   Set up Cloud Firestore.
3.  **Google Maps API**:
    *   Obtain an API key from the [Google Cloud Console](https://console.cloud.google.com/).
    *   Add the key to your `local.properties` or `AndroidManifest.xml`.
4.  **Build and Run**: Open the project in Android Studio and run it on an emulator or physical device.

## 🤝 Contributing

Contributions are welcome! Together, we can make our city cleaner and greener. 🌍

---
**Together for a Cleaner Tomorrow**
