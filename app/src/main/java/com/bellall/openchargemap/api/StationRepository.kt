package com.bellall.openchargemap.api

import com.bellall.openchargemap.model.ChargingStationResponse

interface StationRepository {
    suspend fun getAllStations(): ApiResult<ChargingStationResponse, String>
}