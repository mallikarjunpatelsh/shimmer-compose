# 🌟 Shimmer Compose

A lightweight and flexible **shimmer effect library for Jetpack Compose**, designed to create beautiful loading placeholders with multiple animation styles.

---

## 🎥 Demo

See shimmer effects in action:

[shimmer-compose.webm](https://github.com/user-attachments/assets/57b859a2-0d0f-40f0-a44b-8971642ad58c)


---

## ✨ Features

- ⚡ Simple and clean API  
- 🎨 Multiple shimmer effects  
- 🖼️ Built-in **ShimmerAsyncImage** support  
- 🧩 Easy integration with any composable  
- 🚀 Lightweight and performant  

---

## 📦 Installation

Add the dependency:

```kotlin
implementation("io.github.mallikarjunpatelsh:shimmer-compose:1.0.1")
```

## 🚀 Usage
Apply shimmer to any composable
```kotlin
Box(
    modifier = Modifier
        .size(100.dp)
        .shimmer()
)
```

## Wrap UI with shimmer
```kotlin
ShimmerBox {
    // Your composable here
}
```
## Shimmer Async Image ⭐
```kotlin
ShimmerAsyncImage(
    model              = "https://picsum.photos/seed/shimmer/800/400",
    contentDescription = "Sample image",
    modifier           = Modifier
        .padding(horizontal = 16.dp)
        .fillMaxWidth()
        .height(200.dp),
    shape  = RoundedCornerShape(12.dp),
    effect = ShimmerEffect.DIAGONAL,
)
```
Automatically displays shimmer while the image is loading.

## 🎨 Shimmer Effects
```kotlin
enum class ShimmerEffect {
    HORIZONTAL, // Left → Right
    DIAGONAL,   // 135° sweep
    PULSE,      // Fade in/out
    RADIAL,     // Moving spotlight
    VERTICAL    // Top → Bottom
}
```

## 💡 Example with custom effect
```kotlin
Modifier.shimmer(effect = ShimmerEffect.RADIAL)
```

## ⚙️ Requirements
Minimum SDK: 21+
Jetpack Compose enabled
```kotlin
buildFeatures {
    compose = true
}
🤝 Contributing
```

Contributions, issues, and feature requests are welcome!

📄 License

Apache License 2.0

👨‍💻 Author

Mallikarjun Patel
GitHub: https://github.com/mallikarjunpatelsh
