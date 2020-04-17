# MarsUploadDemo

MarsUploadDemo is an android application written in kotlin to upload images picked from Gallerry and Camera, created with Android Jetpack CameraX API.

### UI Structure and features

1. Application is having two basic views(frgaments) to display images from device(Gallery View) and uploaded images(Upload List) respectively in Bottom Navigation.

2. On first open, gallery fragment will ask for storage permission to fetch images from phone and will display in grids.

3. In Upload List, images those are uploaded will be displayed, images can be zoom In and zoom out.

4. Captured and gallery picked images will goto crop view before upload.


## Application structure
All UI related work is done in fragments and fragments can be found in frgaments folder inside application module.

Application follows MVVM design pattern, and used retrofit networking library to upload images on server

To show image list from device `GalleryFragment` is used which utilized kotlin coroutine to fetch images from mediastore by the help of LiveData.
Same implementation using RxJava can be found inside `ImageListFragment`

On Any thumbnail click will open a `ImageDialogFragment` where image big view will be displayed and can be upload to server, following crop view.

On Clicking of Camera fab button open CamerFragment in full screen. Image can be captured from both front, back facing camera. After image click action performed UploadFragment will be triggered to upload to server, after performing crop action.

### Dependencies

- [RxJava](https://github.com/ReactiveX/RxJava)
- [RxAndroid](https://github.com/ReactiveX/RxAndroid)
- [Room](https://developer.android.com/topic/libraries/architecture/room)
- [Lifecycle-aware components](https://developer.android.com/topic/libraries/architecture/lifecycle)
- [ViewModels](https://developer.android.com/topic/libraries/architecture/viewmodel)
- [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
- [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
- [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- [Dagger2](https://github.com/google/dagger)
- [Retrofit](https://github.com/square/retrofit)
- [CameraX](https://developer.android.com/training/camerax)
## Build

To build the app directly from the command line, run:
```sh
./gradlew assembleDebug
```

## Test

Unit testing and instrumented device testing share the same code. To test the app using Robolectric, no device required, run:
```sh
./gradlew test
```

To run the same tests in an Android device connected via ADB, run:
```sh
./gradlew connectedAndroidTest
```

Alternatively, test running configurations can be added to Android Studio for convenience (and a nice UI). To do that:
1. Go to: `Run` > `Edit Configurations` > `Add New Configuration`.
1. For Robolectric select `Android JUnit`, for connected device select `Android Instrumented Tests`.
1. Select `app` module and `com.nipun.marsuploaddemo.MainInstrumentedTest` class.
1. Optional: Give the run configuration a name, like `test robolectric` or `test device`