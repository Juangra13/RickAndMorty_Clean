package com.jgcb.rickandmorty.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jgcb.rickandmorty.domain.model.Character

@Entity(
    tableName = "character"
)
data class CharacterEntity(
        @ColumnInfo(name = "created") val created: String,
        @ColumnInfo(name = "gender")val gender: String,
        @PrimaryKey @ColumnInfo(name = "id") val id: Int,
        @ColumnInfo(name = "image") val image: String,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "species") val species: String,
        @ColumnInfo(name = "status") val status: String,
        @ColumnInfo(name = "type") val type: String,
        @ColumnInfo(name = "url") val url: String
)

fun CharacterEntity.asExternalModel() = Character(
    created = created,
    gender = gender,
    id = id,
    image = image,
    name = name,
    species = species,
    status = status,
    type = type,
    url = url
)
