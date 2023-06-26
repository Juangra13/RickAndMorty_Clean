package com.jgcb.rickandmorty.utils

/**
 * Created by @Juan Gabriel Corrales on 21/07/2023.
 */

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(
                status = Status.SUCCESS,
                data = data,
                message = null)
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(
                status = Status.LOADING,
                data = data,
                message = null)
        }

        fun <T> error(message: String?, data: T? = null): Resource<T> {
            return Resource(
                status = Status.ERROR,
                data = data,
                message = message)
        }

    }

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }
}
