package com.ilhomsoliev.conferencestestapp.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ilhomsoliev.conferencestestapp.domain.model.Conference

@Entity(tableName = "conference")
data class ConferenceEntity(
    val url: String,
    @ColumnInfo(name = "end_date")
    val endDate: String,
    val icon: String,
    @PrimaryKey(autoGenerate = false)
    val name: String,
    @ColumnInfo(name = "obj_type")
    val objType: String,
    @ColumnInfo(name = "start_date")
    val startDate: String,
    @ColumnInfo(name = "page")
    var page: Int,
)

fun ConferenceEntity.toConference() = Conference(
    endDate = endDate,
    icon = icon,
    name = name,
    objType = objType,
    startDate = startDate,
    url = url,
    page = page
)