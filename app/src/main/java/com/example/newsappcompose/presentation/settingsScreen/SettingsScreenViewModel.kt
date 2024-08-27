package com.example.newsappcompose.presentation.settingsScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsScreenViewModel: ViewModel() {
    private val _isDarkMode = MutableLiveData(false)
    val isDarkMode: LiveData<Boolean> = _isDarkMode

    fun toggleTheme(isDarkMode: Boolean){
        _isDarkMode.value = isDarkMode
    }
}