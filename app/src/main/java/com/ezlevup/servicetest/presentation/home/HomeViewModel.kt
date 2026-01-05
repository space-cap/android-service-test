package com.ezlevup.servicetest.presentation.home

import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ezlevup.servicetest.service.MyBackgroundService
import com.ezlevup.servicetest.service.MyForegroundService

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    /**
     * 포어그라운드 서비스를 시작합니다.
     */
    fun startMyService() {
        val intent = Intent(context, MyForegroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    /**
     * 서비스를 중지합니다.
     */
    fun stopMyService() {
        val intent = Intent(context, MyForegroundService::class.java)
        context.stopService(intent)
    }

    /**
     * 백그라운드 서비스를 시작합니다.
     */
    fun startBackgroundService() {
        val workRequest = OneTimeWorkRequestBuilder<MyBackgroundService>().build()
        WorkManager.getInstance(context).enqueue(workRequest)
    }
}
