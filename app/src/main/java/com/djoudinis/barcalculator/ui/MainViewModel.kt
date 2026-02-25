package com.djoudinis.barcalculator.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.djoudinis.barcalculator.data.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainViewModel : ViewModel() {
    val billItems = mutableStateListOf<BillItem>()
    val bacDrinks = mutableStateListOf<BacDrink>()
    
    // Bar Management
    var currentBarName by mutableStateOf("Meine Bar")
    
    // Configurable Settings
    var standardCocktailPrice by mutableStateOf(5.00)
    var standardBeerPrice by mutableStateOf(2.80)
    var standardShotPrice by mutableStateOf(2.00)
    
    // App State
    var weight by mutableStateOf(80f)
    var hoursSinceFirstDrink by mutableStateOf(0f)
    var isMale by mutableStateOf(true)
    var isDrunkMode by mutableStateOf(false)
    var waterCount by mutableStateOf(0)
    var searchQuery by mutableStateOf("")
    
    // Fun Features
    var lastDiceRoll by mutableStateOf(1)
    var lastSuggestedDrink by mutableStateOf<Drink?>(null)
    var selectedDrinkForPrice by mutableStateOf<Drink?>(null)
    var peakBac by mutableStateOf(0.0)
    
    // Night in Numbers State
    var startTime by mutableStateOf<String?>(null)
    var totalAlcoholGrams by mutableStateOf(0.0)

    val billTotal: Double
        get() = billItems.sumOf { it.price }

    val barsVisitedCount: Int
        get() = billItems.map { it.barName }.distinct().size

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

    val favoriteDrink: String
        get() = billItems.groupBy { it.name }
            .maxByOrNull { it.value.size }?.key ?: "Noch kein Favorit"

    val bacValue: Double
        get() {
            if (bacDrinks.isEmpty()) return 0.0
            val r = if (isMale) 0.68 else 0.55
            var alcoholGrams = 0.0
            bacDrinks.forEach { d ->
                alcoholGrams += d.ml * (d.abv / 100.0) * 0.789
            }
            totalAlcoholGrams = alcoholGrams
            val bac = (alcoholGrams / (weight * r)) - (0.15 * hoursSinceFirstDrink)
            val finalBac = if (bac > 0) bac else 0.0
            if (finalBac > peakBac) peakBac = finalBac
            return finalBac
        }

    val hangoverForecast: Int
        get() {
            val alcFactor = (bacValue * 45).toInt()
            val waterBonus = (waterCount * 12)
            return (alcFactor - waterBonus).coerceIn(0, 100)
        }

    val djoudiniWisdom: String
        get() = when {
            bacValue == 0.0 -> "Nüchtern? Langweilig. Schüttle für einen Drink! 💡"
            bacValue < 0.5 -> "Alles entspannt in '${currentBarName}'. 🍃"
            bacValue < 1.0 -> "Lustige Phase! Trink jetzt ein Glas Wasser. 💧"
            else -> "Djoudini sagt: Taxi rufen, ab nach Hause! 🚕"
        }

    val partyColor: Color
        get() = when {
            bacValue < 0.4 -> Color(0xFFD4AF37) // Gold
            bacValue < 0.8 -> Color(0xFF00E676) // Neon Green
            bacValue < 1.3 -> Color(0xFFFFD600) // Amber
            else -> Color(0xFFFF1744) // Neon Red
        }

    fun addToBill(drink: Drink, customPrice: Double? = null) {
        if (startTime == null) {
            startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        }
        
        val price = customPrice ?: when(drink.type) {
            DrinkType.COCKTAIL -> standardCocktailPrice
            DrinkType.BEER -> standardBeerPrice
            DrinkType.SHOT -> standardShotPrice
        }
        
        val time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())
        // Stamping with current bar name
        billItems.add(BillItem(name = drink.name, price = price, emoji = drink.emoji, abv = drink.abv, time = time, barName = currentBarName))
        
        if (drink.abv > 0) {
            val ml = when(drink.type) {
                DrinkType.SHOT -> 40
                DrinkType.BEER -> if (drink.name.contains("0.5")) 500 else 330
                DrinkType.COCKTAIL -> 250
            }
            addBacDrink(drink.name, drink.abv, ml)
        }
    }

    fun rollDice() { lastDiceRoll = (1..6).random() }
    fun addWater() { waterCount++ }
    fun removeBillItem(item: BillItem) { billItems.remove(item) }
    fun addBacDrink(name: String, abv: Double, ml: Int) { bacDrinks.add(BacDrink(name = name, abv = abv, ml = ml)) }
    fun getRandomSuggestion() { lastSuggestedDrink = DrinkDatabase.allDrinks.random() }
    
    fun getPriceAnalysis(drink: Drink, inputPrice: Double): String {
        val diff = ((inputPrice - drink.avgPrice) / drink.avgPrice) * 100
        return when {
            diff <= 10 -> "✅ Top Preis!"
            diff <= 30 -> "⚠️ Normaler Preis."
            else -> "🚨 ABZOCKE! (+${diff.toInt()}% über Schnitt)"
        }
    }
}
