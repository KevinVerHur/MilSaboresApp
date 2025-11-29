package com.example.milsaboresapp.data.repositories

import com.example.milsaboresapp.data.remote.MealApi
import com.example.milsaboresapp.data.remote.models.Meal

class MealRepository(private val api: MealApi) {
    suspend fun getDesserts(): List<Meal> {
        return api.getDesserts().meals
    }
}