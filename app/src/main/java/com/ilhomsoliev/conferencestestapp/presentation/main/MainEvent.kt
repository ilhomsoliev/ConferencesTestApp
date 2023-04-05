package com.ilhomsoliev.conferencestestapp.presentation.main

sealed class MainEvent {
    data class OnItemClick(val url:String): MainEvent()
}