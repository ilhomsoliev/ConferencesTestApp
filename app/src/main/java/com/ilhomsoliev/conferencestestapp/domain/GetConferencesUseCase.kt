package com.ilhomsoliev.conferencestestapp.domain

import androidx.paging.PagingData
import com.ilhomsoliev.conferencestestapp.domain.model.Conference
import com.ilhomsoliev.conferencestestapp.domain.repository.ConferenceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetConferencesUseCase @Inject constructor(
    private val repository: ConferenceRepository
) {
    operator fun invoke(): Flow<PagingData<Conference>> = repository.getConferences()
}