# 📱 Reviewdapp

Reviewdapp is an **Android application** built with **Kotlin** that allows users to sign up, browse service providers by category, and leave or view reviews.  
The app is designed as a lightweight review platform with authentication and provider listings.

---

## ✨ Features
- 🔑 **User Authentication** (Login & Signup)
- 🏠 **Home Screen** with navigation to categories and providers
- 📂 **Categories** list (with `CategoryAdapter` / `CategoryItem`)
- 👤 **Provider Profiles** (Individual Provider view)
- 📝 **Leave & View Reviews** with a clean `ReviewAdapter`
- 📊 **Local Database** integration (`DatabaseHelper`)
- 🎨 Android UI built with Activities & RecyclerView adapters

---

## 📂 Project Structure
Reviewdapp/
├─ app/
│ ├─ src/main/java/com/example/reviewdapp/
│ │ ├─ MainActivity.kt
│ │ ├─ LoginSignupActivity.kt
│ │ ├─ HomeActivity.kt
│ │ ├─ CategoriesActivity.kt
│ │ ├─ IndividualProviderActivity.kt
│ │ ├─ AccountActivity.kt
│ │ ├─ DatabaseHelper.kt
│ │ ├─ CategoryAdapter.kt
│ │ ├─ ProvidersAdapter.kt
│ │ └─ ReviewAdapter.kt
│ ├─ src/main/AndroidManifest.xml
│ ├─ build.gradle.kts
├─ build.gradle.kts
├─ settings.gradle.kts
└─ gradle.properties


---

## ⚙️ Requirements
- Android Studio (Giraffe+ recommended)
- Kotlin 1.9+
- Gradle (wrapper included in project)
- Minimum SDK: 21 (Android 5.0 Lollipop)

---

## ▶️ Setup & Run
1. Clone the repo:
   ```bash
   git clone https://github.com/whitehatt3r/Reviewdapp.git
   Open the project in Android Studio.

Sync Gradle (File > Sync Project with Gradle Files).

Connect an Android device or start an emulator.

Press Run ▶️.
The app uses a local database helper (DatabaseHelper.kt) for storing users, providers, and reviews.

google-services.json is included for potential Firebase integration (if configured).

ProGuard rules are defined in proguard-rules.pro.

