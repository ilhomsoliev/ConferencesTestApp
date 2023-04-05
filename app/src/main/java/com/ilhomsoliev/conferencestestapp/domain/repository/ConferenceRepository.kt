package com.ilhomsoliev.conferencestestapp.domain.repository

import androidx.paging.PagingData
import com.ilhomsoliev.conferencestestapp.domain.model.Conference
import kotlinx.coroutines.flow.Flow

interface ConferenceRepository {
    fun getConferences(): Flow<PagingData<Conference>>
}