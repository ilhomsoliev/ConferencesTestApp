package com.ilhomsoliev.conferencestestapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilhomsoliev.conferencestestapp.domain.GetConferencesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.plus
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getConferencesUseCase: GetConferencesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainState())
    val state = _state.stateIn(
        viewModelScope + Dispatchers.IO,
        SharingStarted.WhileSubscribed(5000),
        MainState()
    )

    init {
        _state.update {
            it.copy(conferences = getConferencesUseCase())
        }
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            else -> {

            }
        }
    }
}