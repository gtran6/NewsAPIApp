# NewsAPIApp
## Android - MVVM - Repository - Retrofit - Glide - Dagger&Hilt - Room - Kotlin
The purpose of this news project is to follow up MVVM architectural design pattern. This is a News Application which shows the current, popular and relevant news from https://newsapi.org/ API. 

## Functionality
The app's functionality includes:
1. Fetch News data from https://newsapi.org/ & show them in Recycler View with smooth pagination.
2. Search news from the API and show them in recycler view.
3. When an item is selected from `RecyclerView` it will load the detail of the news article.
4. From Details view, when clicking on the top right button, it will link to the webpage of the news article.

## Architecture
The app uses clean architecture with `MVVM(Model View View Model)` design pattern. 
MVVM provides better separation of concern, easier testing, lifecycle awareness, etc.

### Model
Model is generated from `JSON` data into a Kotlin data class.

### ViewModel
Used for fetching today's news, searching news & update states. Also send out the status of the network call like Loading, Success, Error using `sealed` class.

### Dependency Injection
The app uses `Dagger-hilt` as a dependency injection library.

The `AppModule.kt` class provides  `Singleton` reference for `Retrofit`, `Repository` etc.

### Network
The network layer is composed of Repository, ApiService.
`ApiInterface` - Is an interface containing the suspend functions for retrofit API call.

`MainRepository` - Holds the definition of the remote repository call.

## Building

In-order to successfully run & test the application you will need an `api key`.

Go to - **https://newsapi.org/**  and click `Get Api Key`

Now Go to - `app/src/main/java/dat/extra/Utils.kt`

And replace

`const val API_KEY = "YOUR_API_KEY"`
