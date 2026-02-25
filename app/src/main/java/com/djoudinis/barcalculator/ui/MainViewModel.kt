package com.djoudinis.barcalculator.ui

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    var billTotal by mutableStateOf(0.0)
    var bacValue by mutableStateOf(0.0)
    
    fun addDrink(price: Double) {
        billTotal += price
    }
    
    fun addAlcohol() {
        bacValue += 0.1
    }
}
