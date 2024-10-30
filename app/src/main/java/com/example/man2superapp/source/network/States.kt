package com.example.man2superapp.source.network

sealed class States<T>{
    data class Success<T>(val data: T): States<T>()
    data class Failed<T>(val message: String): States<T>()
    class Loading<T>(): States<T>()

    companion object{
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: String) = Failed<T>(message)
        fun <T> loading() = Loading<T>()
    }
}