package com.ezlevup.servicetest.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.ezlevup.servicetest.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * 포어그라운드 서비스를 위한 클래스입니다.
 */
class MyForegroundService : Service() {

    // 코루틴 작업을 위한 Job
    private var job: Job? = null

    override fun onBind(intent: Intent?): IBinder? {
        // onBind는 서비스와 컴포넌트가 통신할 때 사용됩니다.
        // 이 예제에서는 사용하지 않으므로 null을 반환합니다.
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 서비스가 시작될 때 호출됩니다.
        // 포어그라운드 서비스를 시작하고 알림을 표시합니다.
        startForegroundService()

        // 코루틴을 사용하여 1초마다 로그를 출력하는 작업을 시작합니다.
        job = CoroutineScope(Dispatchers.Default).launch {
            for (i in 1..1000) {
                Log.d("MyForegroundService", "Service is running... count: $i")
                delay(1000)
            }
        }

        // START_STICKY는 서비스가 시스템에 의해 종료되었을 때,
        // 시스템 리소스가 충분해지면 서비스를 다시 시작하도록 합니다.
        return START_STICKY
    }

    override fun onDestroy() {
        // 서비스가 종료될 때 호출됩니다.
        super.onDestroy()
        // 코루틴 작업을 취소합니다.
        job?.cancel()
    }

    /**
     * 포어그라운드 서비스를 시작하고 사용자에게 보여줄 알림을 생성합니다.
     */
    private fun startForegroundService() {
        val channelId = "foreground_service_channel"
        val channelName = "Foreground Service Channel"

        // Android Oreo (API 26) 이상에서는 알림 채널이 필요합니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }

        // 사용자에게 보여줄 알림을 설정합니다.
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher) // 알림 아이콘
            .setContentTitle("Foreground Service") // 알림 제목
            .setContentText("Service is running in the background.") // 알림 내용
            .build()

        // 포어그라운드 서비스를 시작합니다.
        // 이 메서드를 호출하면 시스템은 이 서비스가 사용자에게 중요하다고 판단하고
        // 메모리가 부족한 상황에서도 서비스를 종료하지 않으려고 노력합니다.
        startForeground(1, notification)
    }
}
