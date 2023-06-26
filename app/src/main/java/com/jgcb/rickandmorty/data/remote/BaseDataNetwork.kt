package com.jgcb.rickandmorty.data.remote

import com.jgcb.rickandmorty.utils.Resource
import retrofit2.Response

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

abstract class BaseDataNetwork {

    protected suspend fun <T> getResponse(call: suspend () -> Response<T>): Resource<T> {
        try{
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    return Resource.success(body)
                }
            }
            return Resource.error("${response.code()} - ${response.message()}")
        } catch (error: Exception) {
            return Resource.error(error.message ?: error.toString())
        }
    }
}