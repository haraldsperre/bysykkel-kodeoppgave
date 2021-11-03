package com.example.cyclebikes.model.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * The 'station_info' endpoint serves:
 *  'last_updated': timestamp
 *  'data': object containing list of stations w/ info
 */
@JsonClass(generateAdapter = true)
data class StationInfoResponse(
    @Json(name = "last_updated") val updated: Long,
    @Json(name = "data") val stationInfoList: StationInfoList
)

/**
 * The 'data' object contains a list of stations
 */
@JsonClass(generateAdapter = true)
data class StationInfoList(
    @Json(name = "stations") val stationList: List<StationInformation>
)

/**
 * An entry in the Station information list.
 * In our simple app, we need the name (to show on screen)
 * and the id (to join with StationStatus)
 */
@JsonClass(generateAdapter = true)
data class StationInformation(
    @Json(name = "station_id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "address") val address: String,
    @Json(name = "lat") val latitude: Double,
    @Json(name = "lon") val longitude: Double,
    @Json(name = "capacity") val capacity: Int
)

/**
 * The 'station_status' endpoint serves:
 *  'last_updated': timestamp
 *  'data': object containing list of stations w/ status
 */
@JsonClass(generateAdapter = true)
data class StationStatusResponse(
    @Json(name = "last_updated") val updated: Long,
    @Json(name = "data") val stationStatusList: StationStatusList
)

/**
 * The 'data' object contains a list of stations
 */
@JsonClass(generateAdapter = true)
data class StationStatusList(
    @Json(name = "stations") val stationList: List<StationStatus>
)

/**
 * An entry in the Station status list.
 * In out simple app, we need the number of bikes and docks available
 * (to show on screen) and the id (to join with StationInformation)
 */
@JsonClass(generateAdapter = true)
data class StationStatus(
    @Json(name = "station_id") val id: String,
    @Json(name = "is_installed") val isInstalled: Int,
    @Json(name = "is_renting") val isRenting: Int,
    @Json(name = "num_bikes_available") val numBikes: Int,
    @Json(name = "num_docks_available") val numDocks: Int,
    @Json(name = "last_reported") val lastReported: Long,
    @Json(name = "is_returning") val isReturning: Int,

)
