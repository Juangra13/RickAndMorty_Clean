package com.jgcb.rickandmorty.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jgcb.rickandmorty.data.local.entity.PaginationInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaginationInfoDao {
    @Query("SELECT * FROM pagination_info WHERE id = 0")
    fun getPaginationInfo(): Flow<PaginationInfoEntity?>

    @Query("SELECT * FROM pagination_info WHERE id = 0")
    suspend fun getPaginationInfoSync(): PaginationInfoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPaginationInfo(info: PaginationInfoEntity)

    @Query("DELETE FROM pagination_info")
    suspend fun clearPaginationInfo()
}