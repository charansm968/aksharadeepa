# AksharaDeepa 📘✨

AksharaDeepa is an Android learning companion designed mainly for SSLC students. The application provides structured subject-wise learning, chapter tracking, quiz practice, strength analysis, and cloud synchronization for both English and Kannada medium students.

---

# 🚀 Features

## 📚 Multi-Subject Learning
The app supports multiple SSLC subjects including:
- Science
- Mathematics
- Social Science
- English
- Kannada
- Hindi
- Physical Education

Each subject contains:
- Chapter-wise content
- PDF reading support
- Progress tracking
- Quiz practice

---

## 🌐 Medium Support
AksharaDeepa supports:
- English Medium
- Kannada Medium

The selected medium is saved locally and synchronized with Firebase.

---

## 🧠 Smart Quiz System
Features include:
- Chapter-wise quizzes
- Randomized questions
- Quiz score tracking
- Attempt history
- Review system
- Progress analytics

---

## 📈 Strength Mapping
The app analyzes:
- Completed chapters
- Average quiz score
- Reading progress

and calculates:
- Subject mastery percentage
- Weak subjects
- Student learning strength

---

## ☁️ Firebase Cloud Sync
The app includes:
- Firebase Authentication
- Firestore Cloud Backup
- Restore Progress
- Account Login
- Password Reset
- Multi-device sync

Users can:
- Create account
- Login using email/password
- Backup progress
- Restore progress on another device

---

## 🔐 Authentication System
Implemented using Firebase Authentication.

Includes:
- LoginActivity
- Create Account
- Forgot Password
- Logout
- Session persistence

---

## ⚙️ Settings Module
The Settings screen provides:
- Sync Progress
- Restore Progress
- Change Medium
- Logout
- Firebase account status

---

## 📊 Progress Tracking
The application tracks:
- Read progress percentage
- Quiz attempts
- Quiz score
- Completed chapters
- Daily activity
- Study streak

---

# 🏗️ Project Architecture

The application follows a structured Android architecture using:

- Kotlin
- MVVM-style organization
- Room Database
- Firebase
- Navigation Component
- Coroutines
- Flow

---

# 🧩 Main Modules

## UI Layer
Contains:
- Activities
- Fragments
- Adapters
- Navigation
- Layouts

### Important Screens
- SplashActivity
- MainActivity
- LoginActivity
- ChapterReaderActivity
- QuizActivity
- SettingsFragment

---

## Data Layer
Includes:
- Room database
- DAO interfaces
- Repository layer
- Models

### Main Files
- AksharaDatabase.kt
- AksharaRepository.kt
- Models.kt
- Daos.kt

---

## Cloud Layer
Firebase synchronization engine.

### Main File
- CloudSyncManager.kt

Responsible for:
- Uploading progress
- Restoring progress
- Firebase authentication
- Firestore integration

---

# 🗂️ Database Structure

The Room database stores:

## Tables
- chapters
- questions
- quiz_sessions
- daily_activity

---

# 🔥 Firebase Integration

Firebase services used:

## Firebase Authentication
- Email/Password login
- Password reset
- Session handling

## Firebase Firestore
Stores:
- Chapter progress
- Quiz score
- Medium selection
- User sync data

---

# 🧪 Technologies Used

## Languages
- Kotlin
- XML

## Android Libraries
- AndroidX
- Material Design
- Navigation Component
- RecyclerView
- Room Database
- Coroutines
- Lifecycle

## Firebase
- Firebase Auth
- Firebase Firestore
- Google Services

---

# 📁 Important Folder Structure

```text
app/
 ├── src/main/java/com/akshara/deepa/
 │    ├── cloud/
 │    ├── data/
 │    ├── ui/
 │    ├── utils/
 │    └── adapters/
 │
 ├── src/main/res/
 │    ├── layout/
 │    ├── drawable/
 │    ├── navigation/
 │    ├── menu/
 │    └── values/
 │
 └── google-services.json
```

---

# 📱 Key Screens

## Splash Screen
Checks:
- Login status
- App initialization
- Medium preference

---

## Login Screen
Provides:
- Login
- Create Account
- Forgot Password

---

## Home Screen
Displays:
- Subjects
- Progress
- Strength data
- Daily goals

---

## Chapter Reader
Features:
- PDF reading
- Scroll tracking
- Auto progress update
- Chapter completion

---

## Quiz Screen
Features:
- MCQ questions
- Score calculation
- Review answers
- Quiz history

---

## Settings Screen
Features:
- Sync data
- Restore data
- Logout
- Medium switch

---

# ⚡ Setup Instructions

## 1. Clone Project

```bash
git clone <https://github.com/charansm968/aksharadeepa.git>
```

---

## 2. Open in Android Studio

Open the project folder in Android Studio.

---

## 3. Add Firebase Configuration

Place:

```text
google-services.json
```

inside:

```text
app/
```

---

## 4. Sync Gradle

Click:

```text
Sync Project with Gradle Files
```

---

## 5. Build and Run

Run the application on:
- Android Emulator
- Physical Android Device

---

# 🎯 Educational Goal

AksharaDeepa aims to:
- Improve self-learning
- Support rural students
- Track learning consistency
- Identify weak subjects
- Encourage chapter mastery
- Provide structured preparation for SSLC students

---

# 🔮 Future Improvements

Possible future enhancements:
- AI-based recommendations
- Voice assistant
- OCR-based question scanning
- Offline AI tutor
- Personalized study plans
- Gamification system
- Leaderboards
- Parent dashboard
- Performance prediction

---

# 👨‍💻 Developed Using

- Android Studio
- Kotlin
- Firebase
- Room Database
- Material Design

---

# 📄 License

This project is developed for educational and learning purposes.

---

# 👤 Project Owner

**Owner Name:** Charan S M

**College/Organization:** Bapuji Institute of Engineering and Technology Davanagere

**Department:** Computer Science and Engineering

**Project Guide/Mentor:** Chandrashekhar M V

**Email:** charansm968@gmail.com

**GitHub:** https://github.com/charansm968

**LinkedIn:** https://www.linkedin.com/in/charan-s-m-2004jun12

---

# 📌 Additional Information

## Project Version
v6

## Release Date
(May-13-2026)

## Minimum Android Version
Android 7.0 (API 24)

## Target Android Version
Android 15 (API 35)

## Firebase Project ID
akasharadeepa

## Repository Link
https://github.com/charansm968/aksharadeepa.git

---

# 📝 Project Status

Current Status: Completed

---

# ❤️ AksharaDeepa

"Lighting the path of learning through technology."

