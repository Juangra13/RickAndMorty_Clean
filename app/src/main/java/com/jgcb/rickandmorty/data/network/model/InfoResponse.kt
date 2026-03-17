package com.jgcb.rickandmorty.data.network.model

import com.jgcb.rickandmorty.data.local.entity.InfoEntity
import kotlinx.serialization.Serializable

/**
 * Modified by @Juan Gabriel Corrales on 03/03/2026.
 */

@Serializable
data class InfoResponse(
        val count: Int,
        val next: String,
        val pages: Int,
        val prev: String?
)

fun InfoResponse.asEntity() = InfoEntity(
    count = count,
    next = next,
    pages = pages,
    prev = prev
)