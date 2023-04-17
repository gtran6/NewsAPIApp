# News API App
### Android - MVVM - Repository - Retrofit - Glide - Dagger&Hilt - Room - Kotlin
The purpose of this news project is to follow up MVVM architectural design pattern. This is a News Application which shows the current, popular and relevant news from https://newsapi.org/ API. 

### Phone display
<img src="https://user-images.githubusercontent.com/78507684/232587712-4422725d-c1b4-4a12-b276-b43c107c6088.png" width="30%" height="30%">&ensp;<img src="https://user-images.githubusercontent.com/78507684/232588597-a52efd79-f473-4adc-ae1c-537f31bb351d.jpeg" width="30%" height="30%">&ensp;<img src="https://user-images.githubusercontent.com/78507684/232588914-74466a47-8e92-46cc-9f72-e7388aa9a626.png" width="30%" height="30%">

### Search News
<img src="https://user-images.githubusercontent.com/78507684/232589968-efddfd73-f47b-4437-b5fd-e7895230fde5.webm" width="30%" height="30%">

### Save News
<img src="https://user-images.githubusercontent.com/78507684/232590827-9ad2cc7f-9358-49ab-b438-41300c96791b.webm" width="30%" height="30%">

### Web View
<img src="https://user-images.githubusercontent.com/78507684/232591369-4270d4ee-8bd8-45ce-b1fa-dc0bc9d1968d.webm" width="30%" height="30%">

### Search Filter
<img src="https://user-images.githubusercontent.com/78507684/232591905-8717360e-05b4-4ed0-a393-dec234a1dc28.webm" width="30%" height="30%">

### Functionality
The app's functionality includes:
1. Fetch News data from https://newsapi.org/ & show them in Recycler View with smooth pagination.
2. Search news from the API and show them in recycler view.
3. When an item is selected from `RecyclerView` it will load the detail of the news article.
4. From Details view, when clicking on the top right button, it will link to the webpage of the news article.

### Architecture
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

### Building

In-order to successfully run & test the application you will need an `api key`.

Go to - **https://newsapi.org/**  and click `Get Api Key`

Now Go to - `app/src/main/java/dat/extra/Utils.kt`

And replace

`const val API_KEY = "YOUR_API_KEY"`
