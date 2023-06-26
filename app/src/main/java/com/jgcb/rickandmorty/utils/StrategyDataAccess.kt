package com.jgcb.rickandmorty.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

fun <T, A> localOrNetworkOperation(databaseQuery: () -> LiveData<T>,
        networkCall: suspend () -> Resource<A>,
        saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == Resource.Status.SUCCESS) {
            responseStatus.data?.let { saveCallResult(it) }

        } else if (responseStatus.status == Resource.Status.ERROR) {
            emit(Resource.error(responseStatus.message))
            emitSource(source)
        }
    }