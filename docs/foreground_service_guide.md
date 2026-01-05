# 포어그라운드 서비스(Foreground Service) 테스트 앱 개발 가이드

이 문서는 안드로이드의 4대 컴포넌트 중 하나인 포어그라운드 서비스를 테스트하는 간단한 앱의 개발 과정을 안내합니다.

## 1. MyForegroundService.kt 생성

- **파일 경로**: `app/src/main/java/com/ezlevup/servicetest/service/MyForegroundService.kt`
- **주요 기능**:
    - `Service`를 상속받아 포어그라운드 서비스를 구현합니다.
    - 서비스가 시작되면 `onStartCommand` 내에서 `startForeground()`를 호출하여 스스로 포어그라운드로 전환하고, 사용자에게 서비스 실행을 알리는 알림(Notification)을 표시합니다.
    - 코루틴을 사용하여 백그라운드에서 1초마다 로그를 출력하여 서비스가 동작 중임을 확인합니다.
    - 서비스가 종료되면 `onDestroy()`에서 코루틴 작업을 취소합니다.

## 2. HomeScreen.kt 수정

- **파일 경로**: `app/src/main/java/com/ezlevup/servicetest/presentation/home/HomeScreen.kt`
- **주요 기능**:
    - Jetpack Compose를 사용하여 UI를 구성합니다.
    - "서비스 시작" 버튼과 "서비스 중지" 버튼을 추가합니다.
    - "서비스 시작" 버튼 클릭 시, 안드로이드 버전에 따라 분기 처리합니다.
        - **Android Oreo (API 26) 이상**: `startForegroundService()`를 호출하여 서비스를 시작합니다. 이 메서드는 백그라운드에서 5초 이내에 서비스의 `startForeground()`를 호출해야 합니다.
        - **Android Oreo (API 26) 미만**: `startService()`를 호출하여 서비스를 시작합니다.

## 3. AndroidManifest.xml 수정

- **파일 경로**: `app/src/main/AndroidManifest.xml`
- **주요 기능**:
    - `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />` 권한을 추가하여 앱이 포어그라운드 서비스를 실행할 수 있도록 합니다.
    - Android 12 (API 31) 이상에서는 `foregroundServiceType`을 지정해야 하므로, `specialUse` 타입을 사용하기 위해 `<uses-permission android:name="android.permission.FOREGROUND_SERVICE_SPECIAL_USE" />` 권한을 추가합니다.
    - `<service>` 태그에 `android:foregroundServiceType="specialUse"` 속성을 추가하여 서비스 유형을 명시하고, `MyForegroundService`를 시스템에 등록합니다.

## 4. 오류 및 해결: `MissingForegroundServiceTypeException`

- **오류 원인**: Android 12 (API 31) 이상을 타겟으로 하는 앱에서 포어그라운드 서비스를 시작할 때 `AndroidManifest.xml`에 `foregroundServiceType`을 명시하지 않으면 `MissingForegroundServiceTypeException`이 발생합니다.
- **해결**: `AndroidManifest.xml`의 `<service>` 태그에 `android:foregroundServiceType`을 추가하고, 필요에 따라 해당 타입에 맞는 권한을 선언해주어야 합니다. 이 프로젝트에서는 `specialUse` 타입을 사용하고 관련 권한을 추가했습니다.

## 5. 실행 방법

1. Android Studio에서 프로젝트를 빌드하고 실행합니다.
2. 앱이 실행되면 "서비스 시작" 버튼을 클릭합니다.
3. 상단 알림 바에 포어그라운드 서비스 알림이 표시되는지 확인합니다.
4. Logcat에서 "MyForegroundService" 태그로 필터링하여 1초마다 로그가 출력되는지 확인합니다.
5. "서비스 중지" 버튼을 클릭하면 알림이 사라지고 로그 출력이 멈추는지 확인합니다.
