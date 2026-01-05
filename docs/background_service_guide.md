# 백그라운드 서비스 가이드 (WorkManager)

이 문서는 안드로이드에서 `WorkManager`를 사용하여 백그라운드 서비스를 구현하는 간단한 예제를 안내합니다.

## 1. WorkManager 의존성 추가

먼저 `app/build.gradle.kts` 파일에 `WorkManager` 라이브러리 의존성을 추가해야 합니다.

```kotlin
dependencies {
    // ... 다른 의존성들
    implementation("androidx.work:work-runtime-ktx:2.9.0")
}
```

의존성을 추가한 후에는 반드시 Gradle 동기화를 수행해야 합니다.

## 2. Worker 클래스 생성

백그라운드에서 실제 작업을 수행할 `Worker` 클래스를 생성합니다. 이 예제에서는 5초 동안 대기하는 간단한 작업을 수행합니다.

`app/src/main/java/com/ezlevup/servicetest/service/MyBackgroundService.kt`

```kotlin
package com.ezlevup.servicetest.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyBackgroundService(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // 백그라운드에서 수행할 작업을 여기에 구현합니다.
        Log.d("MyBackgroundService", "백그라운드 작업 시작")

        // 5초 동안 대기하는 예제 작업
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        Log.d("MyBackgroundService", "백그라운드 작업 완료")

        // 작업이 성공적으로 완료되었음을 나타냅니다.
        return Result.success()
    }
}
```

## 3. 백그라운드 작업 요청

UI(버튼 클릭 등)에서 백그라운드 작업을 시작하도록 요청할 수 있습니다.

### ViewModel 설정

`HomeViewModel.kt`에 `WorkManager`를 사용하여 작업을 요청하는 함수를 추가합니다.

```kotlin
// ... imports
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ezlevup.servicetest.service.MyBackgroundService

class HomeViewModel(application: Application): AndroidViewModel(application) {
    // ... 기존 코드

    /**
     * 백그라운드 서비스를 시작합니다.
     */
    fun startBackgroundService() {
        val workRequest = OneTimeWorkRequestBuilder<MyBackgroundService>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
```

### UI 설정

`HomeScreen.kt`에 백그라운드 서비스 시작을 위한 버튼을 추가하고 `ViewModel`의 함수를 호출합니다.

```kotlin
// ...
        // "백그라운드 서비스 시작" 버튼입니다.
        Button(onClick = {
            // ViewModel에 서비스 시작을 요청합니다.
            homeViewModel.startBackgroundService()
        }) {
            Text(text = "백그라운드 서비스 시작")
        }
// ...
```

## 4. 실행 및 확인

앱을 실행하고 "백그라운드 서비스 시작" 버튼을 클릭합니다. Android Studio의 Logcat에서 "MyBackgroundService" 태그로 필터링하여 작업 시작 및 완료 로그를 확인할 수 있습니다.