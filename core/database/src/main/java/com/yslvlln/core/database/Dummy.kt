package com.yslvlln.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// TODO remove once we have a real entity
@Entity(tableName = "dummy")
data class Dummy(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String
)