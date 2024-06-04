# NewsAppCompose

NewsAppCompose is a modern Android application designed to provide users with the latest news articles. This app leverages the latest Android development technologies, including Jetpack Compose for UI, Retrofit for networking, Paging 3 for efficient data loading, and follows the principles of Clean Architecture and MVVM for a robust and maintainable codebase.

## Features

- **Jetpack Compose**: Modern declarative UI framework for building native Android UIs.
- **Retrofit**: Type-safe HTTP client for Android and Java to handle network requests.
- **Paging 3**: Helps you load and display pages of data from a larger dataset from local storage or over the network.
- **Clean Architecture**: Follows Uncle Bob's clean architecture principles to create a maintainable and testable codebase.
- **MVVM (Model-View-ViewModel)**: Architectural pattern to separate UI and business logic.

## Screenshots

![News Screen](link-to-your-screenshot)
![Article Screen](link-to-your-screenshot)

## Architecture

The project follows the Clean Architecture guidelines, which divides the code into different layers:

- **Presentation Layer**: Contains the UI and ViewModel logic.
- **Domain Layer**: Contains use cases and business logic.
- **Data Layer**: Contains repositories and data sources (e.g., network, database).

## Tech Stack

- **Jetpack Compose**: For building the UI declaratively.
- **Retrofit**: For making network requests to fetch news articles.
- **Paging 3**: For paginating through the list of news articles.
- **Hilt**: For dependency injection.
- **Kotlin Coroutines**: For managing background threads and async tasks.
- **Coil**: For loading images from the network.

## Getting Started

### Prerequisites

- Android Studio Flamingo or later
- Minimum SDK 21

### Installation

1. Clone the repository:

   ```bash
   git clone https://github.com/your-username/NewsAppCompose.git
   ```

2. Open the project in Android Studio.

3. Build and run the project on an emulator or physical device.

## Usage

1. Launch the app to see the latest news articles in the `NewsScreen`.
2. Navigate through different categories using the bottom navigation bar.
3. Click on an article to read its details.

## Project Structure

```
NewsAppCompose/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/newsappcompose/
│   │   │   │   ├── data/      # Data layer, including repositories and data sources
│   │   │   │   ├── domain/    # Domain layer, including use cases and models
│   │   │   │   ├── presentation/  # Presentation layer, including ViewModels and Composables
│   │   │   │   ├── di/        # Dependency Injection with Hilt
│   │   │   │   ├── utils/     # Utility classes and extensions
│   │   │   ├── res/           # Resources such as layouts, strings, etc.
│   │   ├── AndroidManifest.xml
├── build.gradle
├── settings.gradle
```

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for any changes.

1. Fork the repository.
2. Create your feature branch: `git checkout -b feature/YourFeature`.
3. Commit your changes: `git commit -m 'Add some feature'`.
4. Push to the branch: `git push origin feature/YourFeature`.
5. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgements

- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Retrofit](https://square.github.io/retrofit/)
- [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html)
- [MVVM Architecture](https://developer.android.com/jetpack/guide)
