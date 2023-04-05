package com.ilhomsoliev.conferencestestapp.presentation.main

import androidx.paging.PagingData
import com.ilhomsoliev.conferencestestapp.domain.model.Conference
import kotlinx.coroutines.flow.Flow


data class MainState(
    val conferences: Flow<PagingData<Conference>>? = null,
    val isLoading: Boolean = false,
    val error: String = ""
)