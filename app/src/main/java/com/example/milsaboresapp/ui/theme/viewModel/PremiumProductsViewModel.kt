package com.example.milsaboresapp.ui.theme.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.milsaboresapp.data.remote.models.Meal
import com.example.milsaboresapp.data.repositories.MealRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PremiumProductsViewModel(private val repository: MealRepository) : ViewModel() {

    private val _desserts = MutableStateFlow<List<Meal>>(emptyList())
    val desserts: StateFlow<List<Meal>> = _desserts

    init {
        fetchDesserts()
    }

    private fun fetchDesserts() {
        viewModelScope.launch {
            try {
                _desserts.value = repository.getDesserts()
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}