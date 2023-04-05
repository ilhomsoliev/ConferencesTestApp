package com.ilhomsoliev.conferencestestapp.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ilhomsoliev.conferencestestapp.core.Constants.PAGE_SIZE
import com.ilhomsoliev.conferencestestapp.data.local.ConferenceDatabase
import com.ilhomsoliev.conferencestestapp.data.local.model.ConferenceEntity
import com.ilhomsoliev.conferencestestapp.data.local.model.RemoteKeysEntity
import com.ilhomsoliev.conferencestestapp.data.remote.ConferenceApi
import com.ilhomsoliev.conferencestestapp.data.remote.model.ConferenceDTO
import com.ilhomsoliev.conferencestestapp.data.remote.model.toConferenceEntity
import retrofit2.HttpException
import java.io.IOException


@OptIn(ExperimentalPagingApi::class)
class ConferencesRemoteMediator(
    private val api: ConferenceApi,
    private val database: ConferenceDatabase,
) : RemoteMediator<Int, ConferenceEntity>() {

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ConferenceEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { conferenceEntity ->
            database.getRemoteKeysDao().getRemoteKeyByConferenceID(conferenceEntity.name)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ConferenceEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { conferenceEntity ->
            database.getRemoteKeysDao().getRemoteKeyByConferenceID(conferenceEntity.name)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ConferenceEntity>): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.name?.let { id ->
                database.getRemoteKeysDao().getRemoteKeyByConferenceID(id)
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ConferenceEntity>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        try {
            val apiResponse = api.getConferencesByPage(limit = page * PAGE_SIZE)
            val conferencesResponse = apiResponse.data
            /* Provided API does not work properly, when sending page with 3 as limit it gives the same data every time. So I made this logic, taking last 3 elements each time  */
            val conferences = mutableListOf<ConferenceDTO>()
            conferences.add(conferencesResponse[conferencesResponse.size - 3])
            conferences.add(conferencesResponse[conferencesResponse.size - 2])
            conferences.add(conferencesResponse[conferencesResponse.size - 1])
            val endOfPaginationReached = conferences.isEmpty()

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.getRemoteKeysDao().clearRemoteKeys()
                    database.getConferenceDao().clearAllConferences()
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeyEntities = conferences.map {
                    RemoteKeysEntity(
                        conferenceID = it.name,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                database.getRemoteKeysDao().insertAll(remoteKeyEntities)
                database.getConferenceDao()
                    .insertAll(conferences.map { it.toConferenceEntity(page) })
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }
}