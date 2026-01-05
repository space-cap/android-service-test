package com.ezlevup.servicetest.presentation.home

data class HomeState(
    val isLoading: Boolean = false,
    val isBound: Boolean = false,
    val randomNumber: Int? = null,
)
