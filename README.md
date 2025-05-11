# BetterSnacks

**Elevate your Android app's user experience with BetterSnacks, a powerful and customizable library that enhances the standard Material Design Snackbar.** Building upon the familiar Builder pattern, BetterSnacks introduces a range of engaging features, including dynamic animations, tactile vibrations, auditory cues, custom icons, and comprehensive RTL support.

## ✨ Key Features

* **Versatile Snackbar Types**: Choose from specialized snackbar variations like `UndoSnackbar` for reversible actions, `InfoSnackbar` for informative messages, and `TimedSnackbar` for time-sensitive notifications.
* **Engaging Animations**: Bring your snackbars to life with smooth animations: `Slide`, `Fade`, `Scale`, or opt for no animation (`None`).
* **Haptic Feedback**: Provide subtle tactile feedback with optional vibration on snackbar display.
* **Auditory Cues**: Enhance user awareness with customizable sound effects upon snackbar appearance.
* **Custom Visuals**: Add a personal touch by incorporating custom icons alongside your messages.
* **Layout Adaptability**: Seamlessly support both Right-to-Left (RTL) and Left-to-Right (LTR) layouts for global accessibility.
* **Tailored Aesthetics**: Customize the visual appearance with options for background, text, and action button colors.
* **Intuitive Configuration**: Leverage the chainable Builder pattern for a straightforward and expressive way to configure your snackbars.

---

## ⚙️ Installation

To integrate BetterSnacks into your Android project, simply copy the `com.better_snacks` package directly into your project's source directories. Please ensure that your application is utilizing Material Components for Android.

---

## 🛠️ Usage

Here are examples demonstrating how to implement the different Snackbar types:

### 1. Undo Snackbar

```java
new UndoSnackbar.Builder(this) // Use 'this' if calling from an Activity
    .message("Item deleted")
    .actionText("Undo")
    .onUndo(() -> {
        // Implement your undo logic here
        Log.d("BetterSnacks", "Undo action triggered");
    })
    .animation(BaseSnackbar.AnimationType.SLIDE_IN_BOTTOM)
    .vibrateOnShow(true)
    .soundOnShow(true)
    .backgroundColor(Color.RED)
    .textColor(Color.WHITE)
    .show();
````

### 2\. Info Snackbar

```java
// If you are within a Fragment or have a specific View:
View rootView = findViewById(android.R.id.content);
new InfoSnackbar.Builder(rootView)
    .message("File saved")
    .actionText("Ok")
    .onConfirm(() -> {
        // Handle the confirmation action
        Toast.makeText(this, "File save confirmed", Toast.LENGTH_SHORT).show();
    })
    .animation(BaseSnackbar.AnimationType.FADE_IN)
    .show();
```

### 3\. Timed Snackbar

```java
new TimedSnackbar.Builder(findViewById(R.id.your_coordinator_layout)) // Ensure you have a CoordinatorLayout
    .message("Applying changes in 3 seconds")
    .actionText("Abort")
    .onAction(() -> {
        // Logic to execute when the action button is clicked
        Log.i("BetterSnacks", "Aborting changes...");
    })
    .onTimeout(() -> {
        // Code to run when the snackbar times out
        Toast.makeText(this, "Changes applied automatically", Toast.LENGTH_SHORT).show();
    })
    .animation(BaseSnackbar.AnimationType.NONE)
    .show();
```

-----

## ⚙️ Customization Options

The following methods are available within the Builder for each Snackbar type, allowing for extensive customization:

| Method                     | Description                                          |
| :------------------------- | :--------------------------------------------------- |
| `message(String)`          | Sets the primary message text.                       |
| `actionText(String)`       | Sets the text for the action button.                 |
| `duration(int)`            | Sets the display duration in milliseconds.           |
| `layoutDirection(int)`     | Configures the layout direction (e.g., `View.LAYOUT_DIRECTION_RTL`, `View.LAYOUT_DIRECTION_LTR`). |
| `backgroundColor(int)`     | Sets a custom background color for the snackbar.     |
| `textColor(int)`           | Sets a custom color for the message text.            |
| `actionTextColor(int)`     | Sets a custom color for the action button text.      |
| `icon(Drawable)`           | Displays a custom icon next to the message.          |
| `animation(AnimationType)` | Sets the animation style (`NONE`, `FADE_IN`, `SCALE`, `SLIDE_IN_BOTTOM`). |
| `vibrateOnShow(boolean)`   | Enables or disables vibration on snackbar display.    |
| `vibrationDuration(long)`  | Sets the duration of the vibration in milliseconds.  |
| `soundOnShow(boolean)`     | Enables or disables sound playback on snackbar display. |
| `sound(int resId)`         | Sets a custom sound resource to play.               |

-----

## 🖼️ Example Screenshots

\<p align="center"\>Info Snackbar\</p\>
\<p align="center"\>
\<img src="./Screenshots/Info1.png" alt="info snackbar" width="200"/\>
\</p\>

\<p align="center"\>Undo Snackbar\</p\>
\<p align="center"\>
\<img src="./Screenshots/Undo1.png" alt="undo snackbar" width="200"/\>
\</p\>

\<p align="center"\>Timed Snackbar\</p\>
\<p align="center"\>
\<img src="./Screenshots/Timed1.png" alt="timed snackbar" width="200"/\>
\</p\>

```
