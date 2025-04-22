package com.example.basicapplicationstructure.data

import com.google.gson.annotations.SerializedName

data class MoviesMapper(
    @SerializedName("Title") val title: String,
    @SerializedName("Year") val year: String,
    @SerializedName("Rated") val rated: String,
    @SerializedName("Released") val released: String,
    @SerializedName("Runtime") val runtime: String,
    @SerializedName("Actors") val actors: String,
    @SerializedName("Language") val language: String,
    @SerializedName("imdbRating") val imdbRating: String,
    @SerializedName("Type") val type: String,
    @SerializedName("Images") val images: List<String>?,
)
