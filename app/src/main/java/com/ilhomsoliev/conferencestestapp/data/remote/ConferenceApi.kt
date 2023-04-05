package com.ilhomsoliev.conferencestestapp.data.remote

import com.ilhomsoliev.conferencestestapp.data.remote.model.ConferencesDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface ConferenceApi {

    @GET("service/v2/upcomingGuides/")
    suspend fun getConferencesByPage(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int,
    ): ConferencesDTO
}