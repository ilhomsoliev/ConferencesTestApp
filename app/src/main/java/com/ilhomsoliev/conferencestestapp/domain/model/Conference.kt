package com.ilhomsoliev.conferencestestapp.domain.model

data class Conference(
    val endDate: String,
    val icon: String,
    val name: String,
    val objType: String,
    val startDate: String,
    val url: String,
    val page: Int,
)