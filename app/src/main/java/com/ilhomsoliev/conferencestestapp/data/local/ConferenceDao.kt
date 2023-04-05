package com.ilhomsoliev.conferencestestapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ilhomsoliev.conferencestestapp.data.local.model.ConferenceEntity
@Dao
interface ConferenceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(conferences: List<ConferenceEntity>)

    @Query("Select * From conference order by page")
    fun getConferences(): PagingSource<Int, ConferenceEntity>

    @Query("Delete From conference")
    suspend fun clearAllConferences()
}