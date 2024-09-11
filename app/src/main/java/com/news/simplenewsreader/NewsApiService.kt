package com.news.simplenewsreader

import NewsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("v2/top-headlines")
    fun getTopHeadlines(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = "9afd475f6ef14406b2f22fd31ea03acb",
        @Query("country") country: String = "kr"
    ): Call<NewsResponse>

}