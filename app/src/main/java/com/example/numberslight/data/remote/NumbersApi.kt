package com.example.numberslight.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NumbersApi {
    @GET("json.php")
    suspend fun getNumbers(): List<NumberDto>

    @GET("json.php")
    suspend fun getNumberByName(@Query("name") name: String): NumberDetailDto
}