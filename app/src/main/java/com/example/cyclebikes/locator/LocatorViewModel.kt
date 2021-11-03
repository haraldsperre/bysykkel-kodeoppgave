package com.example.cyclebikes.locator

import android.util.Log
import androidx.lifecycle.*
import com.example.cyclebikes.R
import com.example.cyclebikes.model.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.lang.IllegalArgumentException
import com.example.cyclebikes.model.Result
import com.example.cyclebikes.model.network.*

class LocatorViewModel : ViewModel() {

    private val _stationList = MutableLiveData<List<Station>>()
    val stationList : LiveData<List<Station>> = _stationList

    private val _state = MutableLiveData<Result>()
    val state: LiveData<Result> = _state

    init {
        refreshStationData() // Always get fresh data on startup
    }

    /**
     * Main functionality of the app.
     * Calls the API endpoints station_info.json and station_status.json
     * Gets all stations w/ name from station_info and number of available
     * bikes and docks from station_status. Joins the datasets on station ID
     */
    private fun refreshStationData() = viewModelScope.launch {
        _state.value = Result.loading() // Shows loading indicator and disables refresh button

        val stationInfoResponse = MutableLiveData<StationInfoResponse>()
        val stationStatusResponse = MutableLiveData<StationStatusResponse>()
        val mutableStationList: MutableList<Station> = mutableListOf()

        var stationInfo: StationInformation
        var stationStatus: StationStatus

        try {
            withContext(Dispatchers.IO) {
                // API calls in SanntidApi gets station info and station status for all stations
                stationInfoResponse.postValue(SanntidApi.retrofit.getStations())
                stationStatusResponse.postValue(SanntidApi.retrofit.getStatus())

                // Sort both station lists by ID so we can loop through both lists simultaneously
                val infoList = stationInfoResponse.value?.stationInfoList?.stationList?.sortedBy { it.id }
                val statusList = stationStatusResponse.value?.stationStatusList?.stationList?.sortedBy { it.id }

                // If a list is empty or the lists are not the same size, something is wrong
                if (infoList.isNullOrEmpty() || statusList.isNullOrEmpty()) {
                    throw Exception("no_data")
                } else if (infoList.size != statusList.size) {
                    throw Exception("inconsistent_data")
                }

                /**
                 * Create a Station object for each station in the lists, and add it to
                 * the mutable station list. Finally, add all stations to the view models
                 * stationList attribute, which is observed by the Fragment, and pushed to
                 * the adapter
                 */
                for (index in infoList.indices) {
                    stationInfo = infoList[index]
                    stationStatus = statusList[index]

                    mutableStationList.add(Station(
                        id = stationInfo.id,
                        name = stationInfo.name,
                        numBikes = stationStatus.numBikes,
                        numDocks = stationStatus.numDocks
                    ))
                }
                _stationList.postValue(mutableStationList)
            }
        } catch (e: Exception) {
            val error = when (e.message) {
                "no_data" -> R.string.no_data
                "inconsistent_data" -> R.string.inconsistent_data
                else -> R.string.network_error
            }
            _state.value = Result.error(error)
        } finally {
            if (!stationList.value.isNullOrEmpty()) {
                _state.value = Result.success()
            }
        }
    }

    fun onClickRefresh() {
        refreshStationData()
    }

}

class LocatorViewModelFactory(): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocatorViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocatorViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}