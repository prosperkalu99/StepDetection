# 📱 Android Step Detection with Fallback (Jetpack Compose + Koin)

This Android app measures **perimeter** and **area** by counting steps using the **best available sensor** on the device:

- ✅ **Step Detector** (highest accuracy)
- ✅ **Step Counter** (with baseline delta calculation)
- ✅ **Accelerometer fallback** (peak detection)

Built with:
- Jetpack Compose (UI)
- MVVM architecture
- Koin for Dependency Injection
- Clean modular separation (Sensors, ViewModel, UI)

---

## 📌 Features

- 🚶‍♂️ Automatic **step detection fallback** (Detector → Counter → Accelerometer)
- 📏 **Perimeter** and **area** calculation (shoelace formula)
- 🎨 Clean Compose UI with real-time updates
- 💡 Koin DI setup for clean architecture
- ✅ Works offline, requires **no runtime permissions**

---

## 🛠️ Project Structure
```agsl
└── di/
└── AppModule.kt            # Koin DI setup
└── sensor/
└── StepSensorManager.kt    # Sensor selection + fallback logic
└── viewmodel/
└── StepMeasureViewModel.kt # Step count, area, perimeter calculation
└── ui/
└── StepMeasureScreen.kt    # Compose UI observing ViewModel state
└── MainActivity.kt             # App entry point + Koin setup
```
---

## 🚀 How It Works

- ✅ At app launch, the best available sensor is automatically selected:
    1. **Step Detector** →
    2. **Step Counter** →
    3. **Accelerometer (peak detection fallback)**

- ✅ Each detected step:
    - Increases **step count**
    - Updates **perimeter** = steps × stride length
    - Updates **area** using the **shoelace algorithm** (from step path)
- ✅ Real-time updates are reflected on the UI

---

## ⚙️ How to Build & Run

### 1. Clone the Repository

```bash
git clone https://github.com/your-repo/android-step-detection-fallback.git
```
### 2. Open in Android Studio
    •	Recommended: Android Studio Hedgehog or newer.

### 3. Install & Run
    •	Connect a real Android device (recommended for sensors).
    •	Click Run ▶️ in Android Studio.

---

✅ Permissions
•	No special permissions required
•	All sensors used (Step Detector, Step Counter, Accelerometer) do not require runtime permissions.
•	Works offline, suitable for outdoor and indoor step-based measurements.

⸻

🎁 Potential Improvements (Optional Ideas)
•	📍 Dynamic direction tracking via TYPE_ROTATION_VECTOR
•	📝 Adjustable stride length calibration UI
•	🖼️ Canvas path visualization
•	💾 Export step or area data to file
•	🔋 Battery optimization (using SENSOR_DELAY_NORMAL or foreground services)

⸻

📄 License

Licensed under the MIT License.

⸻

✨ Author

Built by [Prosper] to demonstrate real-world step-based area measurement using modern Android development practices.