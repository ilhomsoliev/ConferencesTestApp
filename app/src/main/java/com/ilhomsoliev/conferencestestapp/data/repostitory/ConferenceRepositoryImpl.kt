package com.ilhomsoliev.conferencestestapp.data.repostitory

import androidx.paging.*
import com.ilhomsoliev.conferencestestapp.core.Constants
import com.ilhomsoliev.conferencestestapp.data.local.ConferenceDatabase
import com.ilhomsoliev.conferencestestapp.data.local.model.toConference
import com.ilhomsoliev.conferencestestapp.data.paging.ConferencesRemoteMediator
import com.ilhomsoliev.conferencestestapp.data.remote.ConferenceApi
import com.ilhomsoliev.conferencestestapp.domain.model.Conference
import com.ilhomsoliev.conferencestestapp.domain.repository.ConferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ConferenceRepositoryImpl(
    private val api: ConferenceApi,
    private val database: ConferenceDatabase
) : ConferenceRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getConferences(): Flow<PagingData<Conference>> {
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGE_SIZE),
            remoteMediator = ConferencesRemoteMediator(
                api = api,
                database = database
            ),
            pagingSourceFactory = { database.getConferenceDao().getConferences() }
        ).flow.map {
            it.map { conference ->
                conference.toConference()
            }
        }
    }

}