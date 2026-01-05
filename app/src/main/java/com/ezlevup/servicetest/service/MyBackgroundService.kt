package com.ezlevup.servicetest.service

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyBackgroundService(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

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
