package com.bellall.openchargemap.api

import com.bellall.openchargemap.model.ChargingStationResponse
import com.bellall.openchargemap.base.BaseDataSource
import kotlinx.coroutines.*
import javax.inject.Inject

class ImpStationRepository @Inject constructor(private val service: ApiInterface)
    : BaseDataSource(), StationRepository {

    override suspend fun getAllStations(): ApiResult<ChargingStationResponse, String> =
        coroutineScope { getResult { service.getAllChargingStations() } }
}