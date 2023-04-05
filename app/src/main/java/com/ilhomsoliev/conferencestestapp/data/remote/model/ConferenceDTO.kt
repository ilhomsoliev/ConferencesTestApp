package com.ilhomsoliev.conferencestestapp.data.remote.model

import com.ilhomsoliev.conferencestestapp.data.local.model.ConferenceEntity


data class ConferenceDTO(
    val endDate: String,
    val icon: String,
    val loginRequired: Boolean,
    val name: String,
    val objType: String,
    val startDate: String,
    val url: String,
)

fun ConferenceDTO.toConferenceEntity(page: Int): ConferenceEntity =
    ConferenceEntity(
        endDate = endDate,
        icon = icon,
        name = name,
        objType = objType,
        startDate = startDate,
        url = url,
        page = page
    )
