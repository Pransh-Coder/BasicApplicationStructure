package com.example.basicapplicationstructure.data

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("totalResultsCount") val totalResultsCount: Int,
    @SerializedName("geonames") val geonames: List<Geonames>,
)

data class Geonames (
    @SerializedName("adminCode1") val adminCode1 : String,
    @SerializedName("lng") val lng : Double,
    @SerializedName("geonameId") val geonameId : Int,
    @SerializedName("toponymName") val toponymName : String,
    @SerializedName("countryId") val countryId : Int,
    @SerializedName("fcl") val fcl : String,
    @SerializedName("population") val population : Int,
    @SerializedName("countryCode") val countryCode : String,
    @SerializedName("name") val name : String,
    @SerializedName("fclName") val fclName : String,
    @SerializedName("adminCodes1") val adminCodes1 : AdminCodes1,
    @SerializedName("countryName") val countryName : String,
    @SerializedName("fcodeName") val fcodeName : String,
    @SerializedName("adminName1") val adminName1 : String,
    @SerializedName("lat") val lat : Double,
    @SerializedName("fcode") val fcode : String
)

data class AdminCodes1 (

    @SerializedName("ISO3166_2") val iSO3166_2 : String
)
