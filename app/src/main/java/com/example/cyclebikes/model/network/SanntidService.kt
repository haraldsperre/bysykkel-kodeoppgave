package com.example.cyclebikes.model.network

import retrofit2.http.*

interface SanntidService {

    @Headers(
        "Client-Identifier: Sperre-kodeoppgave_CycleBikes"
    )

    @GET("station_information.json")
    suspend fun getStations(): StationInfoResponse

    @GET("station_status.json")
    suspend fun getStatus(): StationStatusResponse
}