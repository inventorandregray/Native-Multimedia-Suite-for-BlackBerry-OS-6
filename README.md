# BlackBerry OS 6 Multimedia Suite (Java ME)

A fully native **BlackBerry OS 6 Java ME multimedia app**, similar in structure to the OS 5 suite, but optimized for OS 6 with **touch support**, improved UI, and better media handling.  
While OS 6 introduced improved WebKit integration, this project focuses on **pure Java ME**, using `MainScreen`, `ButtonField`, `BitmapField`, and `VideoControl`.

---

##  Enhancements in OS 6 Version

### 1. Touch Support
- Buttons handle both **trackball/keyboard navigation** and **touch events** (`TouchEvent.CLICK`)

### 2. Optimized UI
- `BitmapField` updates dynamically for the **image slideshow**  
- Buttons use **Runnable callbacks** for cleaner code  

### 3. Improved Video & Audio Playback
- Uses **.3gp format** for wider OS 6 support  
- Stops previous media before starting a new track/video to avoid conflicts  

### 4. Clean Resource Management
- Ensures **media players are closed** on app exit  
- Prevents memory leaks on **low-memory devices**  

---

© Andre Gray 2010
