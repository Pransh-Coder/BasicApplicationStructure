package com.example.basicapplicationstructure.data

import com.google.gson.annotations.SerializedName

data class AnimeResponse(
    @SerializedName("pagination") val pagination: Pagination,
    @SerializedName("data") val data: List<Data>,
)

data class Data(
    @SerializedName("mal_id") val mal_id: String,
    @SerializedName("url") val url: String,
    @SerializedName("images") val images: Images,
    @SerializedName("trailer") val trailer: Trailer?,
    @SerializedName("approved") val approved: Boolean,
    @SerializedName("titles") val titles: List<Titles>,
    @SerializedName("title") val title: String,
    @SerializedName("title_english") val title_english: String,
    @SerializedName("title_japanese") val title_japanese: String,
    @SerializedName("title_synonyms") val title_synonyms: List<String>,
    @SerializedName("type") val type: String,
    @SerializedName("source") val source: String,
    @SerializedName("episodes") val episodes: Int,
    @SerializedName("status") val status: String,
    @SerializedName("airing") val airing: Boolean,
    @SerializedName("aired") val aired: Aired,
    @SerializedName("duration") val duration: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("score") val score: Double,
    @SerializedName("scored_by") val scored_by: Int,
    @SerializedName("rank") val rank: Int,
    @SerializedName("popularity") val popularity: Int,
    @SerializedName("members") val members: String,
    @SerializedName("favorites") val favorites: Int,
    @SerializedName("synopsis") val synopsis: String,
    @SerializedName("background") val background: String,
    @SerializedName("season") val season: String,
    @SerializedName("year") val year: Int,
    @SerializedName("broadcast") val broadcast: Broadcast,
    @SerializedName("producers") val producers: List<Producers>,
    @SerializedName("licensors") val licensors: List<Licensors>,
    @SerializedName("studios") val studios: List<Studios>,
    @SerializedName("genres") val genres: List<Genres>,
    @SerializedName("explicit_genres") val explicit_genres: List<String>,
    //@SerializedName("themes") val themes: List<String>,
    @SerializedName("demographics") val demographics: List<Demographics>,
)

data class Images(
    @SerializedName("webp") val webp: Webp,
    @SerializedName("jpg") val jpg: Jpg,

)

data class Trailer(

    @SerializedName("youtube_id") val youtube_id: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("embed_url") val embed_url: String?,
    @SerializedName("images") val images: Images?,
)


data class Titles(

    @SerializedName("type") val type: String,
    @SerializedName("title") val title: String,
)

data class Aired(

    @SerializedName("from") val from: String,
    @SerializedName("to") val to: String,
    @SerializedName("prop") val prop: Prop,
    @SerializedName("string") val string: String,
)

data class Broadcast(

    @SerializedName("day") val day: String,
    @SerializedName("time") val time: String,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("string") val string: String,
)

data class Producers(

    @SerializedName("mal_id") val mal_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

data class Licensors(

    @SerializedName("mal_id") val mal_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

data class Studios(

    @SerializedName("mal_id") val mal_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

data class Genres(

    @SerializedName("mal_id") val mal_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

data class Demographics(

    @SerializedName("mal_id") val mal_id: Int,
    @SerializedName("type") val type: String,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
)

data class Prop(

    @SerializedName("from") val from: From,
    @SerializedName("to") val to: To,
)

data class From(

    @SerializedName("day") val day: Int,
    @SerializedName("month") val month: Int,
    @SerializedName("year") val year: Int,
)

data class To(

    @SerializedName("day") val day: Int,
    @SerializedName("month") val month: Int,
    @SerializedName("year") val year: Int,
)

data class Pagination(

    @SerializedName("last_visible_page") val last_visible_page: Int,
    @SerializedName("has_next_page") val has_next_page: Boolean,
    @SerializedName("current_page") val current_page: Int,
    @SerializedName("items") val items: Items,
)


data class Items(

    @SerializedName("count") val count: Int,
    @SerializedName("total") val total: Int,
    @SerializedName("per_page") val per_page: Int,
)

data class Jpg (

    @SerializedName("image_url") val image_url : String,
    @SerializedName("small_image_url") val small_image_url : String,
    @SerializedName("large_image_url") val large_image_url : String
)

data class Webp (

    @SerializedName("image_url") val image_url : String,
    @SerializedName("small_image_url") val small_image_url : String,
    @SerializedName("large_image_url") val large_image_url : String
)
