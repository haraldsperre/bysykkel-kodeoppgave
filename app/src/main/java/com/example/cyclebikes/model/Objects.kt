package com.example.cyclebikes.model

/**
 * Data class which holds a value with loading status.
 */

data class Result(val status: Status, val message: Int?, val messageString: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun success(): Result {
            return Result(Status.SUCCESS, null, null)
        }

        fun error(message: Int?, messageString: String? = null): Result {
            return Result(Status.ERROR, message, messageString)
        }

        fun loading(): Result {
            return Result(Status.LOADING, null, null)
        }
    }
}

/**
 * Data class representing a station as shown on screen
 */

data class Station(
    val id: String,
    val name: String,
    val numBikes: Int,
    val numDocks: Int
)