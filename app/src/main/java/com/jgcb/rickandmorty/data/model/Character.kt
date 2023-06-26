package com.jgcb.rickandmorty.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

@Entity(tableName = "characters")
data class Character(
        @SerializedName("created")
        val created: String,
        @SerializedName("gender")
        val gender: String,
        @SerializedName("id")
        @PrimaryKey
        val id: Int,
        @SerializedName("image")
        val image: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("species")
        val species: String,
        @SerializedName("status")
        val status: String,
        @SerializedName("type")
        val type: String,
        @SerializedName("url")
        val url: String
)