package com.example.milsaboresapp.data.remote

import retrofit2.http.GET
import com.example.milsaboresapp.data.remote.models.MealResponse

interface MealApi {
    @GET("filter.php?c=Dessert")
    suspend fun getDesserts(): MealResponse
}
