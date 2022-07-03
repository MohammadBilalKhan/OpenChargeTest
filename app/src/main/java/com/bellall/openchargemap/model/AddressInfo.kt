package com.bellall.openchargemap.model

data class AddressInfo(
    val AccessComments: String,
    val AddressLine1: String,
    val Country: Country,
    val ID: Int,
    val Latitude: Double,
    val Longitude: Double,
    val StateOrProvince: String,
    val Title: String,
    val Town: String
)