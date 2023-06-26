package com.jgcb.rickandmorty.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

data class Info(
        @SerializedName("count")
        val count: Int,
        @SerializedName("next")
        val next: String,
        @SerializedName("pages")
        val pages: Int,
        @SerializedName("prev")
        val prev: Any
)