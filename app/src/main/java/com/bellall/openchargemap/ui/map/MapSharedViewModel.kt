package com.bellall.openchargemap.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.Marker
import com.bellall.openchargemap.api.ApiResult
import com.bellall.openchargemap.api.StationRepository
import com.bellall.openchargemap.base.BaseViewModel
import com.bellall.openchargemap.model.ChargingStationResponse
import com.bellall.openchargemap.model.ChargingStationResponseItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

/**
 * [MapSharedViewModel] View Model to share data between map and detail screen
 * */
@HiltViewModel
class MapSharedViewModel @Inject constructor(private val repository: StationRepository) :
    BaseViewModel() {

    var clickedMarker: Marker? = null

    private val timeInterval = 30000L
    lateinit var job: Job

    protected val hasDataLoaded = MutableLiveData<Boolean>()
    fun hasDataLoadedLiveData(): LiveData<Boolean> {
        return hasDataLoaded
    }

    private val _stationMutableLiveData =
        MutableLiveData<ChargingStationResponse?>()
    val stationMutableList = _stationMutableLiveData
    fun getstationMutableList(): LiveData<ChargingStationResponse?> {
        return _stationMutableLiveData
    }

    private val _stationDetailsLiveData = MutableLiveData<ChargingStationResponseItem?>()
    val stationDetailsData = _stationDetailsLiveData

    private val _detailsIsDismissed = MutableLiveData(false)
    val detailsIsDismissedLiveData = _detailsIsDismissed

    fun startRepeatingJob() {
        isLoading.value = true
        job = repeatingJob()
        job.start()
    }

    fun stopRepeatingJob() {
        job.cancel()
    }

    private fun repeatingJob(): Job =
        CoroutineScope(Dispatchers.Default).launch {
            try {
                while (isActive) {
                    handleAllStationData(repository.getAllStations())
                    delay(timeInterval)
                }
            } catch (e: CancellationException) {
                stopRepeatingJob()
            }
        }

    private fun handleAllStationData(result: ApiResult<ChargingStationResponse, String>) {
        when (result) {
            is ApiResult.NetworkError -> {
                stopLoader()
                networkError.postValue(true)
            }
            is ApiResult.OnSuccess -> {
                stopLoader()
                _stationMutableLiveData.postValue(result.response)
                hasDataLoaded.postValue(true)
                val a = 0
            }
            is ApiResult.OnFailure -> {
                stopLoader()
                displayError.postValue(result.exception)
            }
        }
    }

    fun setClickedMarkerData(item: ChargingStationResponseItem?) {
        _stationDetailsLiveData.value = item
    }

    fun isDetailsDialogDismissed(isDismissed: Boolean) = _detailsIsDismissed.postValue(isDismissed)

    private fun stopLoader() = isLoading.postValue(false)

    override fun onCleared() {
        super.onCleared()
        stopRepeatingJob()
    }

}