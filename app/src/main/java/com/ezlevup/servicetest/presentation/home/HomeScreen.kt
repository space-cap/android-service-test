package com.ezlevup.servicetest.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel()
) {
    // 화면 중앙에 버튼을 배치하기 위한 Column 입니다.
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // "포어그라운드 서비스 시작" 버튼입니다.
        Button(onClick = {
            // ViewModel에 서비스 시작을 요청합니다.
            homeViewModel.startMyService()
        }) {
            Text(text = "포어그라운드 서비스 시작")
        }

        // "포어그라운드 서비스 중지" 버튼입니다.
        Button(onClick = {
            // ViewModel에 서비스 중지를 요청합니다.
            homeViewModel.stopMyService()
        }) {
            Text(text = "포어그라운드 서비스 중지")
        }

        // "백그라운드 서비스 시작" 버튼입니다.
        Button(onClick = {
            // ViewModel에 서비스 시작을 요청합니다.
            homeViewModel.startBackgroundService()
        }) {
            Text(text = "백그라운드 서비스 시작")
        }
    }
}
