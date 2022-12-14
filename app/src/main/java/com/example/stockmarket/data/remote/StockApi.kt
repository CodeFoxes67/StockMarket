package com.example.stockmarket.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    companion object {
        const val API_KEY = "AKDK5A1NCHD1JU5A"
        const val BASE_URL = "https://alphavantage.co"
    }

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody
}