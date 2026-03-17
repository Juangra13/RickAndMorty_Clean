package com.jgcb.rickandmorty.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pagination_info")
data class PaginationInfoEntity(
        @PrimaryKey val id: Int = 0, // Solo habrá un registro
        val currentPage: Int,
        val totalPages: Int,
        val hasNextPage: Boolean
)
