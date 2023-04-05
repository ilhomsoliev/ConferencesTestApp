package com.ilhomsoliev.conferencestestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ilhomsoliev.conferencestestapp.data.local.model.ConferenceEntity
import com.ilhomsoliev.conferencestestapp.data.local.model.RemoteKeysEntity

@Database(
    entities = [ConferenceEntity::class, RemoteKeysEntity::class],
    version = 2
)
abstract class ConferenceDatabase : RoomDatabase() {
    abstract fun getConferenceDao(): ConferenceDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}