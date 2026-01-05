package com.ezlevup.servicetest.presentation.home

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import androidx.lifecycle.AndroidViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ezlevup.servicetest.service.MyBackgroundService
import com.ezlevup.servicetest.service.MyBoundService
import com.ezlevup.servicetest.service.MyForegroundService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private var myBoundService: MyBoundService? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBoundService.MyBinder
            myBoundService = binder.getService()
            _uiState.update { it.copy(isBound = true) }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            myBoundService = null
            _uiState.update { it.copy(isBound = false) }
        }
    }

    fun bindService() {
        Intent(context, MyBoundService::class.java).also { intent ->
            context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    fun unbindService() {
        if (_uiState.value.isBound) {
            context.unbindService(connection)
            _uiState.update { it.copy(isBound = false, randomNumber = null) }
        }
    }

    fun getRandomNumberFromService() {
        if (_uiState.value.isBound) {
            val randomNumber = myBoundService?.getRandomNumber()
            _uiState.update { it.copy(randomNumber = randomNumber) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        unbindService()
    }

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
