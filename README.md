<div align="center">

# Mission Objective

</div>

---

### **Project description**

Mission Objective is a mobile app that gamifies daily routines
The user’s day appears as a map screen similar to a video game's pause menu with objectives plotted as markers (eg, "Eat lunch," "Go for a walk," "Finish task from calendar")
Users can complete, snooze, or start quests directly from the map, earning XP and tracking streaks

The app blends personal productivity with exploration mechanics and a cozy adventure-game aesthetic

---

### **Main goal**

Build a Kotlin-based Android app using Jetpack Compose that visualises daily tasks and calendar events as map-based "quests"

---

### **Key features**

* 🗺️ **Main Map Screen**

    * Interactive map (Google Maps Compose or Mapbox)
    * Daily objectives plotted as quest markers
    * Bottom sheet showing a list of today’s quests

* 🎯 **Objectives & XP System**

    * Custom and pre-set quests (Eat, Drink, Walk)
    * XP and streak tracking for completions
    * Optional light inventory system (bottle, shoes, snack icons)

* 📅 **Calendar Integration**

    * Read daily events
    * Convert select events into quests

* 🚶 **Distance-based quests**

    * Track walking/running progress using location data

* 📊 **Stats / Character Screen**

    * Displays XP, streaks, and completion rate
    * Optional avatar customisation

* 🛠️ **Quest Editor**

    * Add or edit quests manually
    * Set location, time, and type

---

### **Visual style**

Theme: **Cozy hand-drawn map** (from mood board sketches)

* Warm parchment tones, soft shadows, and rounded markers
* UI Font: *Nunito*
* Title Font: *IM Fell English*
* Iconography: Simple line-art or filled icons with accent colours for active quests

Alternate future themes:

* *Tactical scanner* (neon, HUD-like)
* *Sticker map* (bright, playful)

---

### **Tech stack**

* **Language:** Kotlin
* **Framework:** Jetpack Compose
* **Database:** Room
* **Architecture:** MVVM
* **Dependency Injection:** Hilt (optional)
* **Location/Maps:** Google Maps Compose (or Mapbox for custom tiles)
* **Background Jobs:** WorkManager
* **Calendar Access:** Android Calendar Provider
* **Build Tool:** Gradle (KTS)

---

### **MVP milestone plan**

**Week 1**

* Scaffold app (Compose + Navigation + Room)
* Map + bottom sheet with hardcoded quests
* Quest entity CRUD
* Calendar read integration

**Week 2**

* Location + walk quest
* XP/streak logic
* Final UI theme + interactions
* Internal testing

---

### **Stretch goals**

* Health Connect / Google Fit sync
* Notifications and morning "Mission Brief"
* Seasonal themes
* Widgets

---

### **Reference materials**

* Uploaded sketches (inventory, map, mission layout)
* Mood board (x-ray inventory, hand-drawn maps, stylised layouts)
