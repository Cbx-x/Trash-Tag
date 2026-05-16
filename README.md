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

#### 1.Splash Screen -> 2.Login Page -> 3.Registration Page -> 4.Reporter Home Page -> 5.Waste Reporting Page -> 6.Reporter Reward Page 
<img width="150" height="300" alt="Splash Screen" src="https://github.com/user-attachments/assets/62e0b153-db90-454d-b26b-dd1c22144ea8" /> - <img width="150" height="300" alt="Login screen" src="https://github.com/user-attachments/assets/c10801e1-7962-4014-9bbb-44d81aeb308b" /> - <img width="150" height="300" alt="Registration Screen" src="https://github.com/user-attachments/assets/42095e3f-82fb-4f6b-a4ec-a384b7d8fe6a" /> - <img width="150" height="300" alt="Reporter Home page" src="https://github.com/user-attachments/assets/b0692e94-668f-4c5d-bc5a-ba52593b684a" /> - <img width="150" height="300" alt="Waste Reporting Page" src="https://github.com/user-attachments/assets/a97b26c3-bd85-4140-9cd4-f2304204bce8" /> - <img width="150" height="300" alt="Reporter Reward Page" src="https://github.com/user-attachments/assets/3d7d2eae-32a6-4145-b0f1-e12f63dbaf53" />


#### 7.Reporter Maps -> 8.Reporter Profile -> 9.Cleaner Home Page -> 10.Cleaner Maps -> 11.Cleaner Notification Page -> 12.Cleaner Profile 
<img width="150" height="300" alt="Reporter Maps Page" src="https://github.com/user-attachments/assets/97e83220-e392-45b4-83e2-fe7004f3a58b" /> - <img width="150" height="300" alt="Reporter Profile " src="https://github.com/user-attachments/assets/6a4adbb0-be88-4cbc-a5ff-a8587416b22f" /> - <img width="150" height="300" alt="Cleaner Home page" src="https://github.com/user-attachments/assets/2ac1b96b-059a-48ac-a76f-c2f702eb3734" /> - <img width="150" height="300" alt="Cleaner Maps page" src="https://github.com/user-attachments/assets/bc97b9d5-43ed-4d58-89e0-87f1b74ba084" /> - <img width="150" height="300" alt="Notification page" src="https://github.com/user-attachments/assets/d66610a3-7097-4daa-9b65-0af030b4d66a" /> - <img width="150" height="300" alt="Cleaner Profile page" src="https://github.com/user-attachments/assets/2fd568f3-4b10-4f9d-9d9b-60fb2722a279" />


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
