package com.ilhomsoliev.conferencestestapp.data.remote.model


data class ConferenceDTO(
    val endDate: String,
    val icon: String,
    val loginRequired: Boolean,
    val name: String,
    val objType: String,
    val startDate: String,
    val url: String,
)
