# 포어그라운드 서비스(Foreground Service) 테스트 앱 개발 가이드

이 문서는 안드로이드의 4대 컴포넌트 중 하나인 포어그라운드 서비스를 테스트하는 간단한 앱의 개발 과정을 안내합니다.

## 1. MyForegroundService.kt 생성

- **파일 경로**: `app/src/main/java/com/ezlevup/servicetest/service/MyForegroundService.kt`
- **주요 기능**:
    - `Service`를 상속받아 포어그라운드 서비스를 구현합니다.
    - 서비스가 시작되면 `startForegroundService()`를 통해 포어그라운드에서 동작하고, 사용자에게 서비스 실행을 알리는 알림(Notification)을 표시합니다.
    - 코루틴을 사용하여 백그라운드에서 1초마다 로그를 출력하여 서비스가 동작 중임을 확인합니다.
    - 서비스가 종료되면 `onDestroy()`에서 코루틴 작업을 취소합니다.

## 2. HomeScreen.kt 수정

- **파일 경로**: `app/src/main/java/com/ezlevup/servicetest/presentation/home/HomeScreen.kt`
- **주요 기능**:
    - Jetpack Compose를 사용하여 UI를 구성합니다.
    - "서비스 시작" 버튼과 "서비스 중지" 버튼을 추가합니다.
    - 각 버튼 클릭 시 `Intent`를 사용하여 `MyForegroundService`를 시작하거나 중지시킵니다.

## 3. AndroidManifest.xml 수정

- **파일 경로**: `app/src/main/AndroidManifest.xml`
- **주요 기능**:
    - `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />` 권한을 추가하여 앱이 포어그라운드 서비스를 실행할 수 있도록 합니다.
    - `<service>` 태그를 사용하여 `MyForegroundService`를 시스템에 등록합니다.

## 실행 방법

1. Android Studio에서 프로젝트를 빌드하고 실행합니다.
2. 앱이 실행되면 "서비스 시작" 버튼을 클릭합니다.
3. 상단 알림 바에 포어그라운드 서비스 알림이 표시되는지 확인합니다.
4. Logcat에서 "MyForegroundService" 태그로 필터링하여 1초마다 로그가 출력되는지 확인합니다.
5. "서비스 중지" 버튼을 클릭하면 알림이 사라지고 로그 출력이 멈추는지 확인합니다.
