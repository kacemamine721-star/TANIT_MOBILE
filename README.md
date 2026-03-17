# 🌊 TANIT — Flood Intelligence Platform

> **Advanced Android solution for real-time campus flood risk management and proactive safety.**

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android&logoColor=white)](https://developer.android.com/)
[![Java](https://img.shields.io/badge/Language-Java-ED8B00?logo=openjdk&logoColor=white)](https://www.java.com/)
[![OSMdroid](https://img.shields.io/badge/Map-OSMdroid-blue)](https://github.com/osmdroid/osmdroid)
[![Hackathon](https://img.shields.io/badge/Project-Hackathon-red)](https://github.com/)

---

## 📌 Overview

**TANIT** (Tactical Analysis and Networked Intelligence Tool) is a specialized mobile application designed to protect students and faculty from flood risks within the campus. Developed during the **TRC 3.0 Hackathon**, TANIT transforms static academic spaces into "smart" safety zones by providing real-time risk assessments and automated relocation instructions.

### ⚠️ The Problem
Flash floods can rapidly isolate campus buildings, trapping students in vulnerable classrooms and damaging expensive laboratory equipment. Communication during such events is often fragmented and delayed.

### ✅ The Solution
TANIT bridges the gap between flood prediction and user safety. It provides a centralized dashboard where users can see active threats, receive precise relocation orders, and adapt their academic schedule to safer zones instantly.

---

## ✨ Key Features

### 📡 Real-Time Flood Intelligence
- **Dynamic Risk Dashboard:** View current risk levels (Low, Moderate, High) specifically calibrated for campus zones.
- **Saturation Monitoring:** Live alerts based on soil saturation levels (e.g., "78% saturation detected in Salle 17").

### 📍 Intelligent Relocation
- **Automated Room Assignment:** When a classroom becomes vulnerable, TANIT automatically assigns a safe "New Room" (Relocation point).
- **Proactive Instructions:** Clear directives on what to do (e.g., "Disconnect electrical equipment", "Move to Labo 4").

### 📅 Smart Schedule Integration
- **Context-Aware Planning:** Sync your academic schedule with safety data.
- **Relocation Overlays:** If your next class is in a risk zone, your schedule updates dynamically with the new safe location.

### 🔔 High-Priority Notifications
- **Instant Alerts:** Push notifications for critical threats using Android Notification Channels.
- **Severity-Based Branding:** Distinctive UI treatments for different risk levels (Teal for moderate, Red for high).

---

## 🛠️ Tech Stack

- **Core:** Java & Android SDK
- **Architecture:** MVVM-inspired (Adapters, Data Models, Contextual Activities)
- **Maps:** OSMDroid (OpenStreetMap for Android)
- **Data:** JSON Assets for dynamic alert simulation
- **UI/UX:** Material Design with a custom "Cyber-Dark" aesthetic (Glassmorphism & Neon accents)

---

## 📂 Project Structure

```text
tanit_mobile/
├── app/
│   ├── src/main/java/com/example/smartcampusapp/
│   │   ├── MainActivity.java      # Risk Dashboard & Notification Logic
│   │   ├── ScheduleActivity.java  # Adaptive Schedule View
│   │   ├── Alert.java             # Data Model for Flood Events
│   │   └── ...Adapters            # RecyclerView implementations
│   ├── src/main/res/             # UI Layouts & Cyber-Dark Styles
│   └── src/main/assets/          # Real-time data simulations (alerts.json)
├── build.gradle.kts              # Dependency management (OSMdroid, AndroidX)
└── README.md                     # Documentation
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (or newer)
- Android API Level 24+ (Tested on API 36)
- Internet connection (for map tiles)

### Installation
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/tanit-mobile.git
    ```
2.  **Open in Android Studio:**
    Select the `tanit_mobile` folder and wait for Gradle to sync.
3.  **Run the app:**
    Click the **Run** button (Shift + F10) or execute:
    ```bash
    ./gradlew installDebug
    ```

---

## 📐 Architecture & Logic

The application uses a **proactive notification engine** located in `MainActivity.java`. Upon startup, it parses the `alerts.json` intelligence feed, calculates the global campus risk status, and triggers high-priority notifications for any zone with a `high` severity tag.

```java
// Logic snippet for relocation notification
String title = severity.equalsIgnoreCase("high") 
    ? "⚠ ALERTE ÉLEVÉE — " + alert.getClassroom() 
    : "⚡ Alerte — " + alert.getClassroom();
```

---

## 🤝 Team TANIT

*Developed with passion for the TRC 3.0 Hackathon.*

---

## 📜 License

Distributed under the MIT License. See `LICENSE` for more information.

---

<p align="center">
  <img src="tanit-192.png" alt="TANIT Logo" width="100"/>
  <br>
  <i>Empowering Campus Safety through Intelligence.</i>
</p>
