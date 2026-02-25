package com.djoudinis.barcalculator.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.djoudinis.barcalculator.data.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UserDataRepository(application.applicationContext)

    var barVisits by mutableStateOf<List<BarVisit>>(emptyList())
    val bacDrinks = mutableStateListOf<BacDrink>()

    // Configurable Settings
    var standardCocktailPrice by mutableStateOf(7.50)
    var standardBeerPrice by mutableStateOf(3.50)
    var standardShotPrice by mutableStateOf(2.50)
    var standardSoftdrinkPrice by mutableStateOf(2.80)

    // App State
    var weight by mutableStateOf(80f)
    var hoursSinceFirstDrink by mutableStateOf(0f)
    var isMale by mutableStateOf(true)
    var searchQuery by mutableStateOf("")

    // UI State
    var lastSuggestedDrink by mutableStateOf<Drink?>(null)
    var selectedDrinkForPrice by mutableStateOf<Drink?>(null)
    var showNearbyPlaces by mutableStateOf(false)
    var nearbyPlaces by mutableStateOf<List<Pair<String, String>>>(emptyList())

    val currentBarVisit: BarVisit?
        get() = barVisits.lastOrNull()

    init {
        viewModelScope.launch {
            barVisits = repository.barVisitsFlow.first()
            weight = repository.weightFlow.first()
            isMale = repository.isMaleFlow.first()
            recalculateBacDrinks()
        }
    }
    
    val billTotal: Double
        get() = barVisits.sumOf { visit -> visit.billItems.sumOf { it.price } }

    val filteredDrinks: List<Drink>
        get() {
            val base = DrinkDatabase.allDrinks
            return if (searchQuery.isEmpty()) base
            else base.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true) ||
                        it.description.contains(searchQuery, ignoreCase = true)
            }
        }

    val bacValue: Double
        get() {
            if (bacDrinks.isEmpty()) return 0.0
            val r = if (isMale) 0.68 else 0.55
            val alcoholGrams = bacDrinks.sumOf { d -> d.ml * (d.abv / 100.0) * 0.789 }
            val bac = (alcoholGrams / (weight * r)) - (0.15 * hoursSinceFirstDrink)
            return if (bac > 0) bac else 0.0
        }

    val partyColor: Color
        get() = when {
            bacValue < 0.4 -> Color(0xFFD4AF37) // Gold
            bacValue < 0.8 -> Color(0xFF00E676) // Neon Green
            bacValue < 1.3 -> Color(0xFFFFD600) // Amber
            else -> Color(0xFFFF1744) // Neon Red
        }

    fun addOrSwitchBar(barName: String) {
        if (currentBarVisit?.barName != barName) {
            val newVisits = barVisits.toMutableList().apply { add(BarVisit(barName = barName)) }
            updateBarVisits(newVisits)
        }
    }

    fun addToBill(drink: Drink, customPrice: Double? = null) {
        if (currentBarVisit == null) {
             // Potentially prompt user to select a bar first
            addOrSwitchBar("Meine Bar") // Default bar
        }
        
        val price = customPrice ?: when (drink.type) {
            DrinkType.COCKTAIL -> standardCocktailPrice
            DrinkType.BEER -> standardBeerPrice
            DrinkType.SHOT -> standardShotPrice
            DrinkType.SOFTDRINK -> standardSoftdrinkPrice
        }
        
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        val newItem = BillItem(name = drink.name, price = price, emoji = drink.emoji, abv = drink.abv, time = time)

        val newVisits = barVisits.map {
            if (it.barName == currentBarVisit?.barName) {
                it.copy(billItems = it.billItems.toMutableList().apply { add(newItem) })
            } else {
                it
            }
        }
        updateBarVisits(newVisits)
    }

    private fun recalculateBacDrinks() {
        bacDrinks.clear()
        barVisits.flatMap { it.billItems }.forEach { item ->
            if (item.abv > 0) {
                val drinkType = DrinkDatabase.allDrinks.find { it.name == item.name }?.type
                val ml = when (drinkType) {
                    DrinkType.SHOT -> 40
                    DrinkType.BEER -> 330 // Simplified for now
                    DrinkType.COCKTAIL -> 250
                    else -> 0
                }
                if (ml > 0) bacDrinks.add(BacDrink(name = item.name, abv = item.abv, ml = ml))
            }
        }
    }

    private fun updateBarVisits(newVisits: List<BarVisit>) {
        barVisits = newVisits
        recalculateBacDrinks()
        viewModelScope.launch {
            repository.saveBarVisits(barVisits)
        }
    }

    fun saveUserSettings() {
        viewModelScope.launch {
            repository.saveWeight(weight)
            repository.saveIsMale(isMale)
        }
    }

    fun getRandomSuggestion() {
        lastSuggestedDrink = DrinkDatabase.allDrinks.random()
    }
}
