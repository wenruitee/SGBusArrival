package com.buffkatarina.busarrival.data.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


/*Data class to for storing
 deserialised bus routes json data from the api */

@Keep
data class BusStops(
    @SerializedName("odata.metadata")
    val metadata: String,
    @SerializedName("value")
    val data: List<BusStopData>,
) {
    @Keep
    @Entity(tableName = "BusStops")
    data class BusStopData(
        @PrimaryKey
        @SerializedName("BusStopCode")
        val busStopCode: String,

        @SerializedName("RoadName")
        val roadName: String,

        @SerializedName("Description")
        val description: String,

        @SerializedName("Latitude")
        val latitude: Float,

        @SerializedName("Longitude")
        val longitude: Float,
    )
}

