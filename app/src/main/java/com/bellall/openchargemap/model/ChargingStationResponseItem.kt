package com.bellall.openchargemap.model

data class ChargingStationResponseItem(
    val AddressInfo: AddressInfo,
    val ID: Int,
    val NumberOfPoints: Int
)