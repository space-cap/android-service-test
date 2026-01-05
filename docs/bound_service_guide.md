# Bound Service 추가 가이드

이 문서는 앱에 Bound Service를 추가하고 사용하는 방법을 안내합니다.

## 1. Bound Service 생성

`MyBoundService.kt` 파일을 생성하고 다음 코드를 추가합니다.

```kotlin
package com.ezlevup.servicetest.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import kotlin.random.Random

class MyBoundService : Service() {

    private val binder = MyBinder()

    inner class MyBinder : Binder() {
        fun getService(): MyBoundService = this@MyBoundService
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun getRandomNumber(): Int {
        return Random.nextInt(100)
    }
}
```

## 2. AndroidManifest.xml에 서비스 등록

`AndroidManifest.xml` 파일에 `<service>` 요소를 추가하여 서비스를 등록합니다.

```xml
<service
    android:name=".service.MyBoundService"
    android:enabled="true"
    android:exported="false" />
```

## 3. HomeState.kt 수정

`HomeState.kt` 파일에 `isBound`와 `randomNumber` 상태를 추가하여 Bound Service의 연결 상태와 서비스에서 받은 난수를 관리합니다.

```kotlin
package com.ezlevup.servicetest.presentation.home

data class HomeState(
    val isLoading: Boolean = false,
    val isBound: Boolean = false,
    val randomNumber: Int? = null,
)
```

## 4. HomeViewModel.kt 수정

`HomeViewModel.kt` 파일을 수정하여 Bound Service를 제어하고 UI에 데이터를 표시하도록 합니다.

- `StateFlow`를 사용하여 UI 상태를 관리합니다.
- `ServiceConnection`을 구현하여 서비스 연결 및 연결 해제 이벤트를 처리합니다.
- 서비스를 바인드/언바인드하는 함수를 추가합니다.
- 서비스의 `getRandomNumber()` 함수를 호출하여 난수를 가져오는 함수를 추가합니다.
- `onCleared()` 콜백에서 서비스를 언바인드하여 메모리 누수를 방지합니다.

## 5. HomeScreen.kt 수정

`HomeScreen.kt` 파일을 수정하여 UI에서 Bound Service를 제어하고 난수 데이터를 표시하도록 합니다.

```kotlin
// "Bind Service" 버튼입니다.
Button(onClick = {
    if (uiState.isBound) {
        homeViewModel.unbindService()
    } else {
        homeViewModel.bindService()
    }
}) {
    Text(text = if (uiState.isBound) "Unbind Service" else "Bind Service")
}

// "Get Random Number" 버튼입니다.
Button(
    onClick = {
        homeViewModel.getRandomNumberFromService()
    },
    enabled = uiState.isBound
) {
    Text(text = "Get Random Number")
}

// 서비스에서 받은 난수를 표시합니다.
uiState.randomNumber?.let {
    Text(text = "Random Number: $it")
}
```
