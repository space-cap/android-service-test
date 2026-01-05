package com.ezlevup.servicetest.presentation.home

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.ezlevup.servicetest.service.MyForegroundService

@Composable
fun HomeScreen(

) {
    // 화면 중앙에 버튼을 배치하기 위한 Column 입니다.
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        // "서비스 시작" 버튼입니다.
        Button(onClick = {
            // MyForegroundService를 시작하기 위한 Intent를 생성합니다.
            val intent = Intent(context, MyForegroundService::class.java)
            // Android Oreo (API 26) 버전 이상에서는
            // 포어그라운드 서비스를 시작하려면 startForegroundService()를 사용해야 합니다.
            context.startForegroundService(intent)
        }) {
            Text(text = "서비스 시작")
        }

        // "서비스 중지" 버튼입니다.
        Button(onClick = {
            // MyForegroundService를 중지하기 위한 Intent를 생성합니다.
            val intent = Intent(context, MyForegroundService::class.java)
            // 서비스를 중지합니다.
            context.stopService(intent)
        }) {
            Text(text = "서비스 중지")
        }
    }
}